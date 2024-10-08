import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardContent } from "@/components/ui/card";
import { EyeIcon, EyeOffIcon } from "lucide-react";
import { Alert, AlertDescription } from "@/components/ui/alert";
import useLoginForm from '../hooks/useLoginForm';
import l1 from '../assets/humanPointing.png';
import l2 from '../assets/womanPointingAtSomething.png';
import OTPModal from '@/components/OtpModal';

const schema = z.object({
    username: z.string().min(3, "Username must be at least 3 characters long"),
    password: z.string().regex(
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/,
        "Password must be at least 8 characters long, contain one uppercase letter, one lowercase letter, one number, and one special character"
    )
});

const LoginPage = () => {
    const [showPassword, setShowPassword] = useState(false);
    const [generalError, setGeneralError] = useState(null);
    const [isOtpModalOpen, setIsOtpModalOpen] = useState(false);
    const [otpMessage, setOtpMessage] = useState("");

    const { handleLoginForm } = useLoginForm();

    const { register, handleSubmit, formState: { errors } } = useForm({
        resolver: zodResolver(schema)
    });

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    const onSubmit = async (data) => {
        setGeneralError(null);
        try {
            const response = await handleLoginForm(data);

            if (response.status !== 202) {
                // Handle errors for status codes other than 202
                if (response.status === 401) {
                    setGeneralError('Invalid credentials. Please try again.');
                } else if (response.status === 404) {
                    setGeneralError('User not found. Please check your username.');
                } else {
                    setGeneralError('An unexpected error occurred. Please try again.');
                }
            } else {
                setIsOtpModalOpen(!isOtpModalOpen);
                console.log(`Otp Modal status: ${isOtpModalOpen}`)
                setOtpMessage(response.data);
                // Handle 202 status code (success case) in your own logic
                console.log('OTP sent successfully, handle this in your UI logic.');
            }

        } catch (error) {
            // Handle network or other errors
            setGeneralError('An error occurred during login. Please try again.');
            console.error('Login error:', error);
        }
    };


    return (
        <div className="min-h-screen bg-blue-50 flex flex-col justify-between">
            <main className="container mx-auto px-4 py-8 flex-grow flex items-center justify-center">
                <div className="text-center">
                    <h1 className="text-3xl font-bold text-blue-600 mb-8">
                        Access Your Dashboard – Log in Here
                    </h1>
                    <div className='w-full max-w-2xl flex justify-between items-center'>
                        <img
                            src={l2}
                            alt="Decorative figure"
                            className="w-1/5 h-auto hidden md:block"
                        />
                        <Card className="w-full max-w-sm bg-blue-100 shadow-lg mx-auto">
                            <CardContent className="p-6">
                                <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
                                    <div className="space-y-2 text-start">
                                        <label htmlFor="username" className="text-sm font-medium text-blue-800">
                                            Username
                                        </label>
                                        <Input
                                            id="username"
                                            placeholder="Enter Username"
                                            className="bg-white"
                                            {...register('username')}
                                        />
                                        {errors.username && (
                                            <p className="text-red-500 text-sm font-medium mt-1">{errors.username.message}</p>
                                        )}
                                    </div>
                                    <div className="space-y-2 text-start">
                                        <label htmlFor="password" className="text-sm font-medium text-blue-800">
                                            Password
                                        </label>
                                        <div className="relative">
                                            <Input
                                                id="password"
                                                type={showPassword ? "text" : "password"}
                                                placeholder="Enter your password"
                                                className="bg-white pr-10"
                                                {...register('password')}
                                            />
                                            <Button
                                                type="button"
                                                variant="ghost"
                                                size="icon"
                                                className="absolute right-0 top-0 h-full px-3 py-2 hover:bg-transparent"
                                                onClick={togglePasswordVisibility}
                                                aria-label={showPassword ? "Hide password" : "Show password"}
                                            >
                                                {showPassword ? (
                                                    <EyeOffIcon className="h-4 w-4 text-gray-400" />
                                                ) : (
                                                    <EyeIcon className="h-4 w-4 text-gray-400" />
                                                )}
                                            </Button>
                                        </div>
                                        {errors.password && (
                                            <p className="text-red-500 text-sm font-medium mt-1">{errors.password.message}</p>
                                        )}
                                    </div>
                                    <a href="#" className="text-sm text-blue-600 text-start hover:underline block">
                                        Forgot password?
                                    </a>
                                    <Button type="submit" className="w-full bg-blue-500 hover:bg-blue-600 text-white">
                                        LOGIN
                                    </Button>
                                </form>
                                {generalError && (
                                    <Alert variant="destructive" className="mt-4">
                                        <AlertDescription>{generalError}</AlertDescription>
                                    </Alert>
                                )}
                                <a href="/" className="text-sm text-blue-600 hover:underline block mt-4 text-center">
                                    Go back home
                                </a>
                            </CardContent>
                        </Card>
                        <img
                            src={l1}
                            alt="Decorative figure"
                            className="w-1/5 h-auto hidden md:block"
                            style={{ marginTop: '11rem' }}
                        />
                    </div>
                </div>
                <OTPModal
                    isOpen={isOtpModalOpen}
                    onClose={() => setIsOtpModalOpen(!isOtpModalOpen)}
                    message={otpMessage}
                />
            </main>
            <footer className="p-4 text-center text-gray-600">
                <div className="flex justify-center">
                    <p>&copy; {new Date().getFullYear()}</p>
                </div>
            </footer>
        </div>
    );
};

export default LoginPage;
