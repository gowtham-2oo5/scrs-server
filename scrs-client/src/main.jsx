import React from "react";
import ReactDOM from "react-dom/client";
import {
  createBrowserRouter,
  RouterProvider,
  useNavigate,
} from "react-router-dom";
import App from "./App.jsx";
import "./css/index.css";
import { ThemeProvider } from "./utils/ThemeProvider.jsx";
import LoginPage from "./pages/LoginPage.jsx";
import Forbidden from "./pages/Forbidden_403.jsx";
import ProtectedRoute from "./components/ProtectedRoute.jsx";
import { Outlet } from "react-router-dom";
import AdminLayout from "./components/layout/AdminLayout.jsx";
import RoleBasedRedirect from "./components/RoleBasedRedirect.jsx";
import Logout from "./components/Logout.jsx";
import adminRoutes from "./routes/adminRoutes.jsx";
import StudentDashboard from "./pages/Student/StudentDashboard.jsx";
import FacultyDashboard from "./pages/Faculty/FacultyDashboard.jsx";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
  },
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    path: "/dashboard",
    element: <RoleBasedRedirect />,
  },
  {
    path: "/admin",
    element: (
      <ProtectedRoute requiredRole={["ADMIN", "SUPER-ADMIN"]}>
        <AdminLayout>
          <Outlet />
        </AdminLayout>
      </ProtectedRoute>
    ),
    children: adminRoutes,
  },
  {
    path: "/student",
    element: <StudentDashboard />,
  },
  {
    path: "/faculty",
    element: <FacultyDashboard />,
  },
  {
    path: "/forbidden",
    element: <Forbidden />,
  },
  {
    path: "/logout",
    element: <Logout />,
  },
]);

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <ThemeProvider>
      <RouterProvider router={router} />
    </ThemeProvider>
  </React.StrictMode>
);
