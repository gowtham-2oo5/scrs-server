import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { EyeIcon, EyeOffIcon } from "lucide-react";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { Loader2 } from "lucide-react";
import useLoginForm from "../hooks/useLoginForm";
import l1 from "../assets/humanPointing.png";
import l2 from "../assets/womanPointingAtSomething.png";
import OTPModal from "@/components/OtpModal";

const schema = z.object({
  username: z.string().min(3, "Username must be at least 3 characters long"),
  password: z
    .string()
    .regex(
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/,
      "Password must be at least 8 characters long, contain one uppercase letter, one lowercase letter, one number, and one special character"
    ),
});

export default function LoginPage() {
  const [showPassword, setShowPassword] = useState(false);
  const [generalError, setGeneralError] = useState(null);
  const [isOtpModalOpen, setIsOtpModalOpen] = useState(false);
  const [otpMessage, setOtpMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false); // Add loading state
  const [image1Loaded, setImage1Loaded] = useState(false);
  const [image2Loaded, setImage2Loaded] = useState(false);

  const { handleLoginForm, error } = useLoginForm();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: zodResolver(schema),
  });

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const onSubmit = async (data) => {
    setGeneralError(null);
    setIsLoading(true); // Start loading

    try {
      const response = await handleLoginForm(data);

      if (response.status !== 202) {
        setIsLoading(false); // Stop loading on error
        if (response.status === 401) {
          setGeneralError("Invalid credentials. Please try again.");
        } else if (response.status === 404) {
          setGeneralError("User not found. Please check your username.");
        } else {
          setGeneralError("An unexpected error occurred. Please try again.");
        }
      } else {
        setOtpMessage(response.data);
        setIsOtpModalOpen(true);
      }
    } catch (error) {
      setGeneralError("An error occurred during login. Please try again.");
      console.error("Login error:", error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="flex flex-col justify-between min-h-screen bg-blue-50">
      <main className="container flex items-center justify-center flex-grow px-4 py-8 mx-auto">
        <div className="text-center">
          <h1 className="mb-8 text-3xl font-bold text-blue-600">
            Access Your Dashboard – Log in Here
          </h1>
          <div className="flex items-center justify-between w-full max-w-2xl">
            {/* Left image with lazy loading and skeleton loader */}
            <div className="hidden w-1/5 h-auto md:block">
              {!image2Loaded && (
                <div className="w-full h-64 bg-gray-200 rounded-lg animate-pulse"></div> // Skeleton loader
              )}
              <img
                src={l2}
                alt="Decorative figure"
                className={`w-full h-auto transition-opacity duration-500 ${
                  image2Loaded ? "opacity-100" : "opacity-0"
                }`}
                onLoad={() => setImage2Loaded(true)}
                loading="lazy"
              />
            </div>

            {/* Login form */}
            <div className="w-full max-w-sm p-6 mx-auto bg-blue-100 rounded-lg shadow-lg">
              <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
                <div className="space-y-2 text-start">
                  <label
                    htmlFor="username"
                    className="text-sm font-medium text-blue-800"
                  >
                    Username
                  </label>
                  <Input
                    id="username"
                    placeholder="Enter Username"
                    className="bg-white"
                    {...register("username")}
                    disabled={isLoading}
                  />
                  {errors.username && (
                    <p className="mt-1 text-sm font-medium text-red-500">
                      {errors.username.message}
                    </p>
                  )}
                </div>
                <div className="space-y-2 text-start">
                  <label
                    htmlFor="password"
                    className="text-sm font-medium text-blue-800"
                  >
                    Password
                  </label>
                  <div className="relative">
                    <Input
                      id="password"
                      type={showPassword ? "text" : "password"}
                      placeholder="Enter your password"
                      className="pr-10 bg-white"
                      {...register("password")}
                      disabled={isLoading}
                    />
                    <Button
                      type="button"
                      variant="ghost"
                      size="icon"
                      className="absolute top-0 right-0 h-full px-3 py-2 hover:bg-transparent"
                      onClick={togglePasswordVisibility}
                      aria-label={
                        showPassword ? "Hide password" : "Show password"
                      }
                      disabled={isLoading}
                    >
                      {showPassword ? (
                        <EyeOffIcon className="w-4 h-4 text-gray-400" />
                      ) : (
                        <EyeIcon className="w-4 h-4 text-gray-400" />
                      )}
                    </Button>
                  </div>
                  {errors.password && (
                    <p className="mt-1 text-sm font-medium text-red-500">
                      {errors.password.message}
                    </p>
                  )}
                </div>
                <a
                  href="#"
                  className="block text-sm text-blue-600 text-start hover:underline"
                >
                  Forgot password?
                </a>
                <Button
                  type="submit"
                  className="w-full text-white bg-blue-500 hover:bg-blue-600"
                  disabled={isLoading}
                >
                  {isLoading ? (
                    <>
                      <Loader2 className="w-4 h-4 mr-2 animate-spin" />
                      Logging in...
                    </>
                  ) : (
                    "LOGIN"
                  )}
                </Button>
              </form>
              {(generalError || error) && (
                <Alert variant="destructive" className="mt-4">
                  <AlertDescription>{generalError || error}</AlertDescription>
                </Alert>
              )}
              <a
                href="/"
                className="block mt-4 text-sm text-center text-blue-600 hover:underline"
              >
                Go back home
              </a>
            </div>

            {/* Right image with lazy loading and skeleton loader */}
            <div className="hidden w-1/5 h-auto md:block">
              {!image1Loaded && (
                <div className="w-full h-64 bg-gray-200 rounded-lg animate-pulse"></div> // Skeleton loader
              )}
              <img
                src={l1}
                alt="Decorative figure"
                className={`w-full h-auto transition-opacity duration-500 ${
                  image1Loaded ? "opacity-100" : "opacity-0"
                }`}
                onLoad={() => setImage1Loaded(true)}
                loading="lazy"
                style={{ marginTop: "11rem" }}
              />
            </div>
          </div>
        </div>
        <OTPModal
          isOpen={isOtpModalOpen}
          onClose={() => setIsOtpModalOpen(false)}
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
}
