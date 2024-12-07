import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";

const days = [
  "Monday",
  "Tuesday",
  "Wednesday",
  "Thursday",
  "Friday",
  "Saturday",
  "Sunday",
];
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

const getTimeFromSlot = (slot) => {
  const hour = parseInt(slot.split("_")[1]);
  return `${hour + 7}:00 - ${hour + 8}:00`;
};

export function TemplateViewer({ templates, isOpen, onClose }) {
  if (!templates || templates.length === 0) return null;

  const createScheduleMatrix = (slots) => {
    const matrix = timeSlots.map(() => new Array(days.length).fill(null));
    slots.forEach((slot) => {
      const rowIndex = timeSlots.indexOf(slot.timeSlot);
      const colIndex = days.indexOf(
        slot.day.charAt(0).toUpperCase() + slot.day.slice(1).toLowerCase()
      );
      if (rowIndex !== -1 && colIndex !== -1) {
        matrix[rowIndex][colIndex] = slot.courseCategory;
      }
    });
    return matrix;
  };

  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent className="max-w-6xl">
        <DialogHeader>
          <DialogTitle>Schedule Templates</DialogTitle>
        </DialogHeader>
        <div className="mt-4 max-h-[70vh] overflow-auto">
          {templates.map((template, index) => (
            <div key={template.id} className="mb-6">
              <h3 className="mb-2 text-lg font-semibold">{template.title}</h3>
              <p className="mb-2 text-sm text-gray-500">
                Cluster: {template.cluster}
              </p>
              <div className="overflow-x-auto">
                <Table>
                  <TableHeader>
                    <TableRow>
                      <TableHead className="w-24">Time</TableHead>
                      {days.map((day) => (
                        <TableHead key={day} className="text-center">
                          {day}
                        </TableHead>
                      ))}
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {createScheduleMatrix(template.slots).map(
                      (row, rowIndex) => (
                        <TableRow key={rowIndex}>
                          <TableCell className="font-medium">
                            {getTimeFromSlot(timeSlots[rowIndex])}
                          </TableCell>
                          {row.map((cell, cellIndex) => (
                            <TableCell key={cellIndex} className="text-center">
                              {cell ? (
                                <div className="p-2 rounded bg-primary text-primary-foreground">
                                  {cell}
                                </div>
                              ) : null}
                            </TableCell>
                          ))}
                        </TableRow>
                      )
                    )}
                  </TableBody>
                </Table>
              </div>
            </div>
          ))}
        </div>
      </DialogContent>
    </Dialog>
  );
}
