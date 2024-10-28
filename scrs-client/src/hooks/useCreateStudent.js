import { useState } from "react";
import { createStudent } from "@/api";

const useCreateStudent = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);

  const handleCreateStudent = async (formData) => {
    setLoading(true);
    setError(null);
    setSuccess(null);

    try {
      const res = await createStudent(formData);
      if (res.error) {
        throw new Error(res.error);
      }
      setSuccess(res.data);
      return res.data;
    } catch (error) {
      console.error(`Error creating student: ${error.message}`);
      setError(error.message);
      throw error;
    } finally {
      setLoading(false);
    }
  };

  return { handleCreateStudent, loading, error, success };
};

export default useCreateStudent;
