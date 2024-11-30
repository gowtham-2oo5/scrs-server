import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { getSessionItem } from "@/utils/sessionStorageManager";
import { getTokenData } from "@/utils/tokenHandling";
const RoleBasedRedirect = () => {
  const navigate = useNavigate();
  const userRole = getSessionItem("role");
  // console.log(getTokenData(getSessionItem("token")));

  useEffect(() => {
    // console.log("Current user role:", userRole);

    switch (userRole) {
      case "ADMIN":
      case "SUPER-ADMIN":
        navigate("/admin");
        break;
      case "STUDENT":
        navigate("/student");
        break;
      case "FACULTY":
        navigate("/faculty");
        break;
      default:
        navigate("/login");
        break;
    }
  }, [userRole, navigate]);

  return null;
};

export default RoleBasedRedirect;
