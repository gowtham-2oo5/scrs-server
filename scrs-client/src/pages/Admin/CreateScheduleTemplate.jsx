import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { DragDropContext, Droppable, Draggable } from "react-beautiful-dnd";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";

const days = ["MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"];
const timeSlots = [
  "HOUR_1",
  "HOUR_2",
  "HOUR_3",
  "HOUR_4",
  "HOUR_5",
  "HOUR_6",
  "HOUR_7",
  "HOUR_8",
];

const initialCourseCategories = [
  {
    id: "MATH101",
    name: "Mathematics 101",
    minSlots: 2,
    maxSlots: 4,
    currentSlots: 0,
  },
  {
    id: "PHYS101",
    name: "Physics 101",
    minSlots: 2,
    maxSlots: 3,
    currentSlots: 0,
  },
  {
    id: "CHEM101",
    name: "Chemistry 101",
    minSlots: 1,
    maxSlots: 3,
    currentSlots: 0,
  },
  {
    id: "BIO101",
    name: "Biology 101",
    minSlots: 2,
    maxSlots: 4,
    currentSlots: 0,
  },
];

export default function CreateScheduleTemplate() {
  const navigate = useNavigate();
  const [title, setTitle] = useState("");
  const [cluster, setCluster] = useState("");
  const [courseCategories, setCourseCategories] = useState(
    initialCourseCategories
  );
  const [schedule, setSchedule] = useState(
    Object.fromEntries(days.map((day) => [day, {}]))
  );

  const onDragEnd = (result) => {
    const { source, destination } = result;

    if (!destination) return;

    const sourceId = source.droppableId;
    const destId = destination.droppableId;

    if (sourceId === "courseList") {
      const courseCategory = courseCategories.find(
        (c) => c.id === result.draggableId
      );
      if (
        courseCategory &&
        courseCategory.currentSlots < courseCategory.maxSlots
      ) {
        const [day, timeSlot] = destId.split("-");
        setSchedule((prev) => ({
          ...prev,
          [day]: {
            ...prev[day],
            [timeSlot]: courseCategory.id,
          },
        }));
        setCourseCategories((prev) =>
          prev.map((c) =>
            c.id === courseCategory.id
              ? { ...c, currentSlots: c.currentSlots + 1 }
              : c
          )
        );
      }
    } else if (destId === "courseList") {
      const [sourceDay, sourceTimeSlot] = sourceId.split("-");
      const courseCategoryId = schedule[sourceDay]?.[sourceTimeSlot];
      if (courseCategoryId) {
        setSchedule((prev) => ({
          ...prev,
          [sourceDay]: {
            ...prev[sourceDay],
            [sourceTimeSlot]: null,
          },
        }));
        setCourseCategories((prev) =>
          prev.map((c) =>
            c.id === courseCategoryId
              ? { ...c, currentSlots: c.currentSlots - 1 }
              : c
          )
        );
      }
    } else {
      const [sourceDay, sourceTimeSlot] = sourceId.split("-");
      const [destDay, destTimeSlot] = destId.split("-");
      const courseCategoryId = schedule[sourceDay]?.[sourceTimeSlot];
      if (courseCategoryId) {
        setSchedule((prev) => ({
          ...prev,
          [sourceDay]: {
            ...prev[sourceDay],
            [sourceTimeSlot]: null,
          },
          [destDay]: {
            ...prev[destDay],
            [destTimeSlot]: courseCategoryId,
          },
        }));
      }
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const slots = [];
    for (const day in schedule) {
      for (const timeSlot in schedule[day]) {
        const courseCategory = schedule[day][timeSlot];
        if (courseCategory) {
          slots.push({ day, timeSlot, courseCategory });
        }
      }
    }
    const templateData = {
      slots,
      title,
      cluster,
    };
    console.log("Template Data:", JSON.stringify(templateData, null, 2));
    navigate("/admin/schedule/manage");
  };

  return (
    <div className="p-8">
      <DragDropContext onDragEnd={onDragEnd}>
        <div className="container py-8 mx-auto">
          <button
            onClick={() => navigate(-1)}
            className="text-sm font-medium text-gray-900 underline hover:text-gray-700"
          >
            ← Go Back
          </button>

          <h1 className="mt-4 mb-6 text-3xl font-bold">Create New Template</h1>
          <form onSubmit={handleSubmit} className="space-y-6">
            <div>
              <Label htmlFor="title">Title</Label>
              <Input
                id="title"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                required
              />
            </div>
            <div>
              <Label htmlFor="cluster">Cluster</Label>
              <Input
                id="cluster"
                value={cluster}
                onChange={(e) => setCluster(e.target.value)}
                required
              />
            </div>
            <div className="flex space-x-4">
              <div className="w-1/4">
                <h3 className="mb-2 text-lg font-semibold">
                  Course Categories
                </h3>
                <Droppable droppableId="courseList">
                  {(provided) => (
                    <div
                      {...provided.droppableProps}
                      ref={provided.innerRef}
                      className="space-y-2"
                    >
                      {courseCategories.map((course, index) => (
                        <Draggable
                          key={course.id}
                          draggableId={course.id}
                          index={index}
                        >
                          {(provided) => (
                            <div
                              ref={provided.innerRef}
                              {...provided.draggableProps}
                              {...provided.dragHandleProps}
                              className="p-2 rounded bg-primary text-primary-foreground"
                            >
                              {course.name} ({course.currentSlots}/
                              {course.maxSlots})
                            </div>
                          )}
                        </Draggable>
                      ))}
                      {provided.placeholder}
                    </div>
                  )}
                </Droppable>
              </div>
              <div className="w-3/4">
                <h3 className="mb-2 text-lg font-semibold">Schedule</h3>
                <div className="grid grid-cols-6 gap-2">
                  <div></div>
                  {days.map((day) => (
                    <div key={day} className="font-semibold text-center">
                      {day}
                    </div>
                  ))}
                  {timeSlots.map((timeSlot) => (
                    <React.Fragment key={timeSlot}>
                      <div className="font-semibold text-right">
                        {timeSlot.replace("HOUR_", "")}
                      </div>
                      {days.map((day) => (
                        <Droppable
                          key={`${day}-${timeSlot}`}
                          droppableId={`${day}-${timeSlot}`}
                        >
                          {(provided, snapshot) => (
                            <div
                              ref={provided.innerRef}
                              {...provided.droppableProps}
                              className={`border p-2 h-16 ${
                                snapshot.isDraggingOver
                                  ? "bg-gray-100"
                                  : "bg-white"
                              }`}
                            >
                              {schedule[day]?.[timeSlot] && (
                                <Draggable
                                  draggableId={`${day}-${timeSlot}`}
                                  index={0}
                                >
                                  {(provided) => (
                                    <div
                                      ref={provided.innerRef}
                                      {...provided.draggableProps}
                                      {...provided.dragHandleProps}
                                      className="p-1 text-sm rounded bg-primary text-primary-foreground"
                                    >
                                      {
                                        courseCategories.find(
                                          (c) =>
                                            c.id === schedule[day][timeSlot]
                                        )?.name
                                      }
                                    </div>
                                  )}
                                </Draggable>
                              )}
                              {provided.placeholder}
                            </div>
                          )}
                        </Droppable>
                      ))}
                    </React.Fragment>
                  ))}
                </div>
              </div>
            </div>
            <Button type="submit">Create Template</Button>
          </form>
        </div>
      </DragDropContext>
    </div>
  );
}
