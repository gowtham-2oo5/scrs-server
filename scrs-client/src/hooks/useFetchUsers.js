import { useState, useEffect } from "react";
import getUsers from "../api";

const useFetchusers = () => {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchusers = async () => {
      try {
        const response = await getUsers();
        console.log("API Response:", response); // Log the full response
        const data = await response.data; // Ensure the data is accessed correctly
        console.log("Fetched Data:", data); // Log the data
        setUsers(data);
      } catch (err) {
        console.error("Error fetching users:", err);
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchusers();
  }, []);

  return { users, loading, error };
};

export default useFetchusers;
