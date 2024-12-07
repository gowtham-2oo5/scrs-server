import React from "react";
import { useDrag } from "react-dnd";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { ItemTypes } from "@/utils/clusterTemplateUtils";

function DraggableCourse({ course }) {
  const [{ isDragging }, drag] = useDrag(() => ({
    type: ItemTypes.COURSE,
    item: { id: course.id },
    collect: (monitor) => ({
      isDragging: !!monitor.isDragging(),
    }),
  }));

  return (
    <div
      ref={drag}
      className={`p-2 w-full rounded-md shadow-sm transition-colors text-sm ${
        isDragging ? "opacity-50" : ""
      }`}
      style={{
        backgroundColor: course.color,
        color: "#000000",
      }}
    >
      <div className="truncate">{course.name}</div>
      <div className="mt-1 text-xs">
        ({course.currentSlots}/{course.maxSlots})
      </div>
    </div>
  );
}

export function CourseList({ courseCategories }) {
  return (
    <Card className="w-full lg:w-1/3">
      <CardHeader>
        <CardTitle>Course Categories</CardTitle>
      </CardHeader>
      <CardContent>
        <div className="space-y-2">
          {courseCategories.map((course) => (
            <DraggableCourse key={course.id} course={course} />
          ))}
        </div>
      </CardContent>
    </Card>
  );
}
