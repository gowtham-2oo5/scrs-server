import React from "react";
import { useDrop } from "react-dnd";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { X } from "lucide-react";
import { DAYS, TIME_SLOTS, ItemTypes } from "@/utils/clusterTemplateUtils";

function ScheduleSlot({ day, timeSlot, course, onMoveCourse, onRemoveCourse }) {
  const [{ isOver }, drop] = useDrop(() => ({
    accept: ItemTypes.COURSE,
    drop: (item) => onMoveCourse(item.id, day, timeSlot),
    collect: (monitor) => ({
      isOver: !!monitor.isOver(),
    }),
  }));

  return (
    <div
      ref={drop}
      className={`border p-1 h-16 transition-colors ${
        isOver ? "bg-secondary border-primary" : "bg-background"
      }`}
    >
      {course && (
        <div
          className="relative flex items-center justify-between h-full p-1 text-xs text-center rounded-sm"
          style={{
            backgroundColor: course.color,
            color: "#000000",
          }}
        >
          <div className="flex-grow truncate">{course.name}</div>
          <Button
            variant="ghost"
            size="icon"
            className="absolute top-0 right-0 w-4 h-4"
            onClick={() => onRemoveCourse(day, timeSlot)}
          >
            <X className="w-3 h-3" />
            <span className="sr-only">Remove course</span>
          </Button>
        </div>
      )}
    </div>
  );
}

export function ScheduleGrid({
  courseCategories,
  schedule,
  onMoveCourse,
  onRemoveCourse,
}) {
  return (
    <Card className="w-full overflow-x-auto lg:w-2/3">
      <CardHeader>
        <CardTitle>Schedule</CardTitle>
      </CardHeader>
      <CardContent>
        <div className="grid grid-flow-col gap-2 auto-cols-min">
          <div className="sticky left-0 z-10 bg-background">
            <div className="h-8"></div>
            {TIME_SLOTS.map((timeSlot) => (
              <div
                key={timeSlot}
                className="flex items-center justify-end h-16 pr-2 font-semibold"
              >
                {timeSlot.replace("HOUR_", "")}
              </div>
            ))}
          </div>
          {DAYS.map((day) => (
            <div key={day} className="min-w-[120px]">
              <div className="flex items-center justify-center h-8 font-semibold">
                {day}
              </div>
              {TIME_SLOTS.map((timeSlot) => (
                <ScheduleSlot
                  key={`${day}-${timeSlot}`}
                  day={day}
                  timeSlot={timeSlot}
                  course={courseCategories.find(
                    (c) => c.id.toString() === schedule[day]?.[timeSlot]
                  )}
                  onMoveCourse={onMoveCourse}
                  onRemoveCourse={onRemoveCourse}
                />
              ))}
            </div>
          ))}
        </div>
      </CardContent>
    </Card>
  );
}
