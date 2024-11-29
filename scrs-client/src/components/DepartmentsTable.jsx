import { Button } from "@/components/ui/button";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Pencil, Trash2 } from "lucide-react";

export default function DepartmentTable({
  departments,
  onEdit,
  onDelete,
  currentPage,
  itemsPerPage,
}) {
  return (
    <div className="overflow-hidden border border-gray-300 rounded-lg shadow-md">
      <Table className="min-w-full divide-y divide-gray-200">
        <TableHeader>
          <TableRow>
            <TableCell className="px-4 py-2 text-sm font-semibold tracking-wider uppercase">
              ID
            </TableCell>
            <TableCell className="px-4 py-2 text-sm font-semibold tracking-wider uppercase">
              Name
            </TableCell>
            <TableCell className="px-4 py-2 text-sm font-semibold tracking-wider uppercase">
              SN
            </TableCell>
            <TableCell className="px-4 py-2 text-sm font-semibold tracking-wider uppercase">
              HOD
            </TableCell>
            <TableCell className="px-4 py-2 text-sm font-semibold tracking-wider uppercase">
              Actions
            </TableCell>
          </TableRow>
        </TableHeader>
        <TableBody>
          {departments.map((dept, index) => {
            const dynamicIndex = (currentPage - 1) * itemsPerPage + index + 1;
            return (
              <TableRow
                key={dept.id}
                className={`${index % 2 === 0 ? "" : "bg-white"} hover:`}
              >
                <TableCell className="px-4 py-2 text-sm ">
                  {dynamicIndex}
                </TableCell>
                <TableCell className="px-4 py-2 text-sm ">
                  {dept.deptName}
                </TableCell>
                <TableCell className="px-4 py-2 text-sm ">{dept.sn}</TableCell>
                <TableCell className="px-4 py-2">
                  <span
                    className={`px-2 py-1 text-sm rounded-md ${
                      dept.hod
                        ? "bg-green-200 text-green-800"
                        : "bg-gray-200 text-gray-800"
                    }`}
                  >
                    {dept.hod ? dept.hod.name : "Not Set"}
                  </span>
                </TableCell>
                <TableCell className="px-4 py-2">
                  <div className="flex space-x-2">
                    <Button
                      onClick={() => onEdit(dept)}
                      className="flex items-center px-3 py-1 text-sm text-white bg-gray-800 rounded-md hover:bg-gray-700"
                      title="Edit Department"
                    >
                      <Pencil className="w-4 h-4 mr-1" />
                      Edit
                    </Button>
                    <Button
                      onClick={() => onDelete(dept.id)}
                      className="flex items-center px-3 py-1 text-sm text-white bg-red-500 rounded-md hover:bg-red-600"
                      title="Delete Department"
                    >
                      <Trash2 className="w-4 h-4 mr-1" />
                      Delete
                    </Button>
                  </div>
                </TableCell>
              </TableRow>
            );
          })}
        </TableBody>
      </Table>
    </div>
  );
}
