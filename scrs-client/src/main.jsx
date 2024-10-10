import React from "react";
import ReactDOM from "react-dom/client";
import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";
import App from "./App.jsx";
import "./css/index.css";
import { ThemeProvider } from "./utils/ThemeProvider.jsx";
import LoginPage from "./pages/LoginPage.jsx";
import AdminDashboard from "./pages/Admin/AdminDashboard.jsx";
import CreateNewAdmin from "./pages/Admin/CreateNewAdmin.jsx";
import Forbidden from "./pages/Forbidden_403.jsx";
import ProtectedRoute from "./components/ProtectedRoute.jsx";
import { Outlet } from "react-router-dom";
import AdminLayout from "./components/layout/AdminLayout.jsx";
import CreateNewStudent from "./pages/Admin/CreateNewStudent.jsx";
import RoleBasedRedirect from "./components/RoleBasedRedirect.jsx";

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
    element: < RoleBasedRedirect />
  },
  {
    path: "/admin",
    element: (
      <ProtectedRoute requiredRole={['ADMIN', 'SUPER-ADMIN']}>
        <AdminLayout>
          <Outlet />
        </AdminLayout>
      </ProtectedRoute>
    ),
    children: [
      {
        path: "",
        element: <AdminDashboard />,
      },
      {
        path: "courses/manage",
        element: <div>Manage Courses Page (To be created)</div>,
      },
      {
        path: "courses/add",
        element: <div>Add New Course Page (To be created)</div>,
      },
      {
        path: "courses/categories",
        element: <div>Course Categories Page (To be created)</div>,
      },
      {
        path: "schedule/view",
        element: <div>View Schedules Page (To be created)</div>,
      },
      {
        path: "schedule/create",
        element: <div>Create Schedule Page (To be created)</div>,
      },
      {
        path: "schedule/time-slots",
        element: <div>Manage Time Slots Page (To be created)</div>,
      },
      {
        path: "students/list",
        element: <div>Student List Page (To be created)</div>,
      },
      {
        path: "students/add",
        element: < CreateNewStudent />,
      },
      {
        path: "students/registrations",
        element: <div>Student Registrations Page (To be created)</div>,
      },
      {
        path: "faculty/list",
        element: <div>Faculty List Page (To be created)</div>,
      },
      {
        path: "faculty/add",
        element: <div>Add Faculty Page (To be created)</div>,
      },
      {
        path: "faculty/assign-courses",
        element: <div>Assign Courses Page (To be created)</div>,
      },
      {
        path: "registration/course",
        element: <div>Course Registration Page (To be created)</div>,
      },
      {
        path: "registration/periods",
        element: <div>Registration Periods Page (To be created)</div>,
      },
      {
        path: "registration/waitlist",
        element: <div>Waitlist Management Page (To be created)</div>,
      },
      {
        path: "create",
        element: (
          <ProtectedRoute requiredRole={["SUPER-ADMIN"]}>
            <CreateNewAdmin />
          </ProtectedRoute>
        ),
      },
    ],
  },
  {
    path: "/forbidden",
    element: <Forbidden />,
  },
]);

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <ThemeProvider>
      <RouterProvider router={router} />
    </ThemeProvider>
  </React.StrictMode>
);
