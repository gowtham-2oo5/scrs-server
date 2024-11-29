import { Navigate } from "react-router-dom";
import { getSessionItem } from "@/utils/sessionStorageManager";
// Protected Route Wrapper
const ProtectedRoute = ({ children, requiredRole }) => {
  const userRole = getSessionItem("role");

  if (!userRole) {
    return <Navigate to="/login" />; // Redirect to login if not authenticated
  }

  if (!requiredRole.includes(userRole)) {
    return <Navigate to="/forbidden" />; // Redirect to forbidden page if unauthorized
  }

  return children;
};

export default ProtectedRoute;
