import React from "react";
import { Link } from "react-router-dom";
function StudentDashboard() {
  return (
    <div className="grid h-screen place-items-center">
      <h1 className="text-4xl font-bold text-gray-800">Student Dashboard</h1>
      <Link to="/login">Logout</Link>
    </div>
  );
}

export default StudentDashboard;
