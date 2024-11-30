// hooks/useLoginForm.js
import { useState } from "react";
import { loginUser } from "@/api/auth"; // Adjust the path if necessary

const useLoginForm = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleLoginForm = async (formData) => {
    setLoading(true);
    setError(null);

    try {
      const data = await loginUser(formData); // Call the API function
      //const data = {formData}
      return data; // Return the API response data
    } catch (err) {
      setError(err.message); // Set the error state
      throw err; // Rethrow the error to be handled in the component
    } finally {
      setLoading(false);
    }
  };

  return { handleLoginForm, loading, error }; // Return necessary values to the component
};

export default useLoginForm; // This should be a default export
