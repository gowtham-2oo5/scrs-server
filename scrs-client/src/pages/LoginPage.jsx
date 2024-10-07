import React, { useState } from 'react';
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardContent } from "@/components/ui/card";
import { EyeIcon, EyeOffIcon } from "lucide-react";
import useLoginForm from '../hooks/useLoginForm'; // Correct for default export
import l1 from '../assets/humanPointing.png'
import l2 from '../assets/womanPointingAtSomething.png';
import { Alert, AlertDescription } from "@/components/ui/alert";

const LoginPage = () => {
    const [showPassword, setShowPassword] = useState(false);
    const [formData, setFormData] = useState({ username: '', password: '' });
    const [errors, setErrors] = useState({});
    const [generalError, setGeneralError] = useState(null);
    const { handleLoginForm } = useLoginForm();

    const togglePasswordVisibility = () => {
        setShowPassword(!showPassword);
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevData => ({ ...prevData, [name]: value }));
        setErrors(prevErrors => ({ ...prevErrors, [name]: undefined }));
    };

    const validateForm = () => {
        const newErrors = {};

        if (formData.username.length < 3) {
            newErrors.username = "Username must be at least 3 characters long";
        }

        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        if (!passwordRegex.test(formData.password)) {
            newErrors.password = "Password must be at least 8 characters long, contain one uppercase letter, one lowercase letter, one number, and one special character";
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };


    const handleSubmit = async (e) => {
        e.preventDefault();
        setGeneralError(null);

        if (validateForm()) {
            try {
                const response = await handleLoginForm(formData);
                console.log('Login response:', response);
                // Handle successful login here (e.g., redirect to dashboard)
            } catch (error) {
                setGeneralError('An error occurred during login. Please try again.');
                console.error('Login error:', error);
            }
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
                                <form onSubmit={handleSubmit} className="space-y-4">
                                    <div className="space-y-2 text-start">
                                        <label htmlFor="username" className="text-sm font-medium text-blue-800">
                                            Username
                                        </label>
                                        <Input
                                            id="username"
                                            name="username"
                                            placeholder="Enter Username"
                                            className="bg-white"
                                            value={formData.username}
                                            onChange={handleInputChange}
                                        />
                                        {errors.username && (
                                            <p className="text-red-500 text-sm font-medium mt-1">{errors.username}</p>
                                        )}
                                    </div>
                                    <div className="space-y-2 text-start">
                                        <label htmlFor="password" className="text-sm font-medium text-blue-800">
                                            Password
                                        </label>
                                        <div className="relative">
                                            <Input
                                                id="password"
                                                name="password"
                                                type={showPassword ? "text" : "password"}
                                                placeholder="Enter your password"
                                                className="bg-white pr-10"
                                                value={formData.password}
                                                onChange={handleInputChange}
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
                                            <p className="text-red-500 text-sm font-medium mt-1">{errors.password}</p>
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
