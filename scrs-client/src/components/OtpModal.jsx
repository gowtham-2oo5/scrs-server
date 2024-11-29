import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import { AlertCircle, Loader2 } from "lucide-react";
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert";
import {
  InputOTP,
  InputOTPGroup,
  InputOTPSlot,
} from "@/components/ui/input-otp";
import useVerifyOtp from "@/hooks/useVerifyOtp";
import { setItems } from "@/utils/sessionStorageManager";

export default function OTPModal({ message, isOpen, onClose }) {
  const [otp, setOtp] = useState("");
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const { isLoading, error: otpError, verifyOtp } = useVerifyOtp();

  useEffect(() => {
    if (otpError) {
      setError(otpError);
    }
  }, [otpError]);

  const handleComplete = (value) => {
    setOtp(value);
    setError(null);
  };

  const handleSubmit = async () => {
    if (otp.length === 6) {
      setError(null);
      try {
        console.log(`Entered OTP: ${otp}`);
        const { data: user, status } = await verifyOtp(otp);
        if (status === 202) {
          //
          setItems(user.data);

          //sessionStorage.setItem('user', JSON.stringify(user));
          // sessionStorage.setItem('role', user.data.role);
          // sessionStorage.setItem('dp', user.data.profilePicture);
          // 
          onClose();
          console.log(user);

          navigate("/dashboard");
        } else {
          setError("Invalid OTP. Please try again.");
        }
      } catch (err) {
        setError("An error occurred. Please try again.");
      }
    } else {
      setError("Please enter a valid 6-digit OTP");
    }
  };

  return (
    <Dialog className="z-50" open={isOpen} onOpenChange={() => onClose()}>
      <DialogContent
        className="sm:max-w-[425px] bg-blue-100"
        aria-describedby={undefined}
      >
        <DialogHeader>
          <DialogTitle className="text-center text-blue-600">
            Verify Your Email
          </DialogTitle>
        </DialogHeader>
        <div className="flex flex-col items-center space-y-4">
          <Label htmlFor="otp-input" className="text-center text-blue-800">
            {message}
          </Label>
          <InputOTP maxLength={6} onComplete={handleComplete} id="otp-input">
            <InputOTPGroup>
              <InputOTPSlot index={0} />
              <InputOTPSlot index={1} />
              <InputOTPSlot index={2} />
              <InputOTPSlot index={3} />
              <InputOTPSlot index={4} />
              <InputOTPSlot index={5} />
            </InputOTPGroup>
          </InputOTP>
          {error && (
            <Alert variant="destructive">
              <AlertCircle className="w-4 h-4" />
              <AlertTitle>Error</AlertTitle>
              <AlertDescription>{error}</AlertDescription>
            </Alert>
          )}
          <Button
            onClick={handleSubmit}
            className="w-full text-white bg-blue-500 hover:bg-blue-600"
            disabled={isLoading || otp.length !== 6}
          >
            {isLoading ? (
              <>
                <Loader2 className="w-4 h-4 mr-2 animate-spin" />
                Verifying
              </>
            ) : (
              "Verify OTP"
            )}
          </Button>
        </div>
      </DialogContent>
    </Dialog>
  );
}
