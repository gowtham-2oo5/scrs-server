import React from "react";
import ProtectedRoute from "@/components/ProtectedRoute";
import CreateNewAdmin from "@/pages/Admin/CreateNewAdmin";
import CreateNewStudent from "@/pages/Admin/CreateNewStudent";
import AdminDashboard from "@/pages/Admin/AdminDashboard";
import DepartmentManagement from "@/pages/Admin/ManageDepartments";
import SpecializationManagement from "@/pages/Admin/ManageSpecializations";
import BatchManagement from "@/pages/Admin/ManageBatches";
import ManageCourseCategories from "@/pages/Admin/ManageCourseCategories";
import { ManageAdmins } from "@/pages/Admin/ManageAdmins";
import ManageFaculty from "@/pages/Admin/ManageFaculty";
import ManageCourses from "@/pages/Admin/ManageCourses";
import ManageSchedules from "@/pages/Admin/ManageScheduleTemplates";
import { element } from "prop-types";
import CreateScheduleTemplate from "@/pages/Admin/CreateScheduleTemplate";

const adminRoutes = [
  { path: "", element: <AdminDashboard /> },
  {
    path: "courses/manage",
    element: <ManageCourses />,
  },
  {
    path: "courses/add",
    element: <div>Add New Course Page (To be created)</div>,
  },
  {
    path: "courses/categories",
    element: <ManageCourseCategories />,
  },
  {
    path: "schedule/manage",
    element: <ManageSchedules />,
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
    path: "students/manage",
    element: <div>Student manage Page (To be created)</div>,
  },
  {
    path: "students/registrations",
    element: <div>Student Registrations Page (To be created)</div>,
  },
  {
    path: "faculty/manage",
    element: <ManageFaculty />,
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
    path: "departments/manage",
    element: <DepartmentManagement />,
  },
  {
    path: "specializations/manage",
    element: <SpecializationManagement />,
  },
  {
    path: "batches/manage",
    element: <BatchManagement />,
  },
  {
    path: "schedule/clusters",
    element: <div>Manage Clusters Page (To be created)</div>,
  },
  {
    path: "students/bulk-upload",
    element: <div>Bulk Upload Students Page (To be created)</div>,
  },
  {
    path: "registration/audit-logs",
    element: <div>Audit Logs Page (To be created)</div>,
  },
  {
    path: "rooms/list",
    element: <div>View All Rooms Page (To be created)</div>,
  },
  {
    path: "rooms/manage",
    element: <div>Manage Room Allocations Page (To be created)</div>,
  },
  {
    path: "rooms/manual-adjust",
    element: <div>Manual Adjustments Page (To be created)</div>,
  },
  {
    path: "notifs",
    element: <div>Notifications & Alerts Page (To be created)</div>,
  },
  { path: "help", element: <div>Help & Support Page (To be created)</div> },
  { path: "schedule/create-template", element: <CreateScheduleTemplate /> },
  {
    path: "create",
    element: (
      <ProtectedRoute requiredRole={["SUPER-ADMIN"]}>
        <CreateNewAdmin />
      </ProtectedRoute>
    ),
  },
  {
    path: "manage",
    element: (
      <ProtectedRoute requiredRole={["SUPER-ADMIN"]}>
        <ManageAdmins />
      </ProtectedRoute>
    ),
  },
];

export default adminRoutes;
