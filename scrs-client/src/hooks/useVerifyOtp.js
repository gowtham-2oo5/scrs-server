import { useState } from "react";
import { verifyGivenOtp } from "../api/index";

const useVerifyOtp = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const verifyOtp = async (otp) => {
    setIsLoading(true);
    setError(null);
    try {
      const { data, status } = await verifyGivenOtp(otp);
      return { data, status };
    } catch (err) {
      setError(err.message || "Failed to verify OTP");
      return { status: 400 };
    } finally {
      setIsLoading(false);
    }
  };

  return { isLoading, error, verifyOtp };
};

export default useVerifyOtp;
