"use client";

import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { TemplateCard } from "@/components/TemplateCard";
import { TemplateViewer } from "@/components/TemplateViewer";
import { Button } from "@/components/ui/button";
import { PlusCircle } from "lucide-react";
const initialTemplates = [
  {
    id: "1",
    title: "Computer Science 101",
    description: "Introductory computer science course schedule",
    lastModified: "2023-06-20",
    content: JSON.stringify([
      {
        slots: [
          { day: "MONDAY", timeSlot: "HOUR_2", courseCategory: "CS101" },
          { day: "WEDNESDAY", timeSlot: "HOUR_2", courseCategory: "CS101" },
          { day: "FRIDAY", timeSlot: "HOUR_2", courseCategory: "CS101" },
        ],
        title: "Computer Science 101",
        cluster: "CS",
      },
    ]),
  },
  {
    id: "2",
    title: "Data Structures",
    description: "Advanced data structures course schedule",
    lastModified: "2023-06-19",
    content: JSON.stringify([
      {
        slots: [
          { day: "TUESDAY", timeSlot: "HOUR_3", courseCategory: "DS201" },
          { day: "THURSDAY", timeSlot: "HOUR_3", courseCategory: "DS201" },
        ],
        title: "Data Structures",
        cluster: "CS",
      },
    ]),
  },
  {
    id: "3",
    title: "Web Development",
    description: "Full-stack web development course schedule",
    lastModified: "2023-06-18",
    content: JSON.stringify([
      {
        slots: [
          { day: "MONDAY", timeSlot: "HOUR_4", courseCategory: "WD301" },
          { day: "WEDNESDAY", timeSlot: "HOUR_4", courseCategory: "WD301" },
          { day: "FRIDAY", timeSlot: "HOUR_4", courseCategory: "WD301" },
        ],
        title: "Web Development",
        cluster: "WebDev",
      },
    ]),
  },
];

export default function ManageSchedules() {
  const [templates, setTemplates] = useState(initialTemplates);
  const [viewingTemplate, setViewingTemplate] = useState(null);
  const [isViewerOpen, setIsViewerOpen] = useState(false);
  const navigate = useNavigate(); // Use useNavigate from react-router-dom

  const handleView = (template) => {
    setViewingTemplate(template);
    setIsViewerOpen(true);
  };

  const handleEdit = (template) => {
    console.log("Editing template:", template.id);
  };

  const handleDelete = (template) => {
    setTemplates(templates.filter((t) => t.id !== template.id));
  };

  const handleCreateNew = () => {
    navigate("/admin/schedule/create-template"); // Assuming "create-template" is part of adminRoutes
  };

  return (
    <div className="container p-8 mx-auto">
      <div className="flex items-center justify-between mb-9">
        <h1 className="text-3xl font-bold">Manage Schedules</h1>
        <Button onClick={handleCreateNew}>
          <PlusCircle className="w-4 h-4 mr-2" /> Create New Template
        </Button>
      </div>
      {templates.length > 0 ? (
        <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
          {templates.map((template) => (
            <TemplateCard
              key={template.id}
              template={template}
              onView={handleView}
              onEdit={handleEdit}
              onDelete={handleDelete}
            />
          ))}
        </div>
      ) : (
        <p className="text-center text-gray-500">
          No templates available. Create one to get started!
        </p>
      )}
      <TemplateViewer
        template={viewingTemplate}
        isOpen={isViewerOpen}
        onClose={() => setIsViewerOpen(false)}
      />
    </div>
  );
}
