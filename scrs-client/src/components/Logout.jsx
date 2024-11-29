import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { clearItems } from "@/utils/sessionStorageManager";
const Logout = () => {
  const navigate = useNavigate();

  useEffect(() => {
    clearItems();
    navigate("/");
  }, [navigate]);

  return null;
};

export default Logout;
