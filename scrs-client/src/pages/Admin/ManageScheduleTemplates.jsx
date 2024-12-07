"use client";

import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import { TemplateCard } from "@/components/TemplateCard";
import { TemplateViewer } from "@/components/TemplateViewer";
import { Button } from "@/components/ui/button";
import { PlusCircle } from "lucide-react";
import { getAllTemplates } from "@/api/scheduleTemplate";

export default function ManageSchedules() {
  const [templates, setTemplates] = useState([]);
  const [viewingTemplates, setViewingTemplates] = useState([]);
  const [isViewerOpen, setIsViewerOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchTemplates = async () => {
      setIsLoading(true);
      const result = await getAllTemplates();
      console.log(result.data);
      if (result.data) {
        setTemplates(result.data);
        setError(null);
      } else {
        setError(result.error || "Failed to fetch templates");
      }
      setIsLoading(false);
    };

    fetchTemplates();
  }, []);

  const handleView = (template) => {
    setViewingTemplates([template]);
    setIsViewerOpen(true);
  };

  const handleEdit = (template) => {
    console.log("Editing template:", template.id);
    // Implement edit functionality here
    // For example: router.push(`/admin/schedule/edit-template/${template.id}`);
  };

  const handleDelete = (template) => {
    setTemplates(templates.filter((t) => t.id !== template.id));
    // Implement actual delete API call here
  };

  const handleCreateNew = () => {
    navigate("/admin/schedule/create-template");
  };

  if (isLoading) {
    return <div className="container p-8 mx-auto">Loading...</div>;
  }

  if (error) {
    return (
      <div className="container p-8 mx-auto text-red-500">Error: {error}</div>
    );
  }

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
        templates={viewingTemplates}
        isOpen={isViewerOpen}
        onClose={() => setIsViewerOpen(false)}
      />
    </div>
  );
}
