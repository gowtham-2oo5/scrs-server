import { useState } from "react";
import { createAdmin } from "../api"; // Import the API call for creating admin

const useCreateAdmin = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);

  const handleCreateAdmin = async (formData) => {
    setLoading(true);
    setError(null);
    setSuccess(null);

    try {
      const response = await createAdmin(formData); // Call the API function to create an admin
      setSuccess(response.data); // Handle success case
      return response.data;
    } catch (err) {
      console.error("Error creating admin:", err);
      setError(err.response ? err.response.data : err.message);
      throw err; // Rethrow error so it can be caught in the component
    } finally {
      setLoading(false);
    }
  };

  return { handleCreateAdmin, loading, error, success };
};

export default useCreateAdmin;
