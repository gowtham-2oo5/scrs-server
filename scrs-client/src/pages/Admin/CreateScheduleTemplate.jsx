import React, { useState, useEffect } from "react";
import { DndProvider } from "react-dnd";
import { HTML5Backend } from "react-dnd-html5-backend";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Card,
  CardHeader,
  CardTitle,
  CardContent,
  CardFooter,
} from "@/components/ui/card";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb";
import { useSchedule } from "@/hooks/useSchedule";
import { CourseList } from "@/components/CourseCategoriesList";
import { ScheduleGrid } from "@/components/ScheduleGrid";
import { ProgressBar } from "@/components/ProgressBar";
import { getAllBatches } from "@/api/batches";
import { createTemplate } from "@/api/scheduleTemplate";

export default function CreateTemplatePage() {
  const navigate = useNavigate();
  const [title, setTitle] = useState("");
  const [batchId, setBatchId] = useState("");
  const [batches, setBatches] = useState([]);
  const { courseCategories, schedule, moveCourse, removeCourse, isLoading } =
    useSchedule();

  useEffect(() => {
    const fetchBatches = async () => {
      const result = await getAllBatches();
      if (result.data) {
        setBatches(
          result.data.map((batch) => ({ label: batch.name, value: batch.id }))
        );
      }
    };
    fetchBatches();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const slots = Object.entries(schedule).flatMap(([day, timeSlots]) =>
      Object.entries(timeSlots)
        .filter(([, courseCategory]) => courseCategory)
        .map(([timeSlot, courseCategory]) => ({
          day,
          timeSlot,
          courseCategory: courseCategory,
        }))
    );
    const templateData = {
      slots,
      title,
      batchId,
    };

    try {
      const result = await createTemplate(templateData);
      if (result.status >= 200) {
        // Redirect to manage page after successful creation
        navigate("/admin/schedule/manage");
      } else {
        // Handle error (you might want to show an error message to the user)
        console.error("Failed to create template:", result.error);
      }
    } catch (error) {
      console.error("Error creating template:", error);
    }
  };

  if (isLoading) {
    return <div>Loading...</div>;
  }

  return (
    <DndProvider backend={HTML5Backend}>
      <div className="container px-4 py-8 mx-auto">
        <Breadcrumb>
          <BreadcrumbList>
            <BreadcrumbItem>
              <BreadcrumbLink href="/admin/schedule/manage">
                Manage
              </BreadcrumbLink>
            </BreadcrumbItem>
            <BreadcrumbSeparator />
            <BreadcrumbItem>
              <BreadcrumbPage>Create Template</BreadcrumbPage>
            </BreadcrumbItem>
          </BreadcrumbList>
        </Breadcrumb>
        <Card className="mt-4">
          <CardHeader>
            <CardTitle className="text-3xl font-bold">
              Create New Template
            </CardTitle>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit} className="space-y-6">
              <div className="grid grid-cols-1 gap-6 md:grid-cols-2">
                <div>
                  <Label htmlFor="title">Title</Label>
                  <Input
                    id="title"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    required
                    className="mt-1"
                  />
                </div>
                <div>
                  <Label htmlFor="batch">Batch</Label>
                  <Select onValueChange={setBatchId} value={batchId}>
                    <SelectTrigger className="mt-1">
                      <SelectValue placeholder="Select a batch" />
                    </SelectTrigger>
                    <SelectContent>
                      {batches.map((batch) => (
                        <SelectItem key={batch.value} value={batch.value}>
                          {batch.label}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>
              </div>
              <ProgressBar courseCategories={courseCategories} />
              <div className="flex flex-col space-y-4 lg:flex-row lg:space-y-0 lg:space-x-4">
                <CourseList courseCategories={courseCategories} />
                <ScheduleGrid
                  courseCategories={courseCategories}
                  schedule={schedule}
                  onMoveCourse={moveCourse}
                  onRemoveCourse={removeCourse}
                />
              </div>
            </form>
          </CardContent>
          <CardFooter>
            <Button type="submit" onClick={handleSubmit} className="w-full">
              Create Template
            </Button>
          </CardFooter>
        </Card>
      </div>
    </DndProvider>
  );
}
