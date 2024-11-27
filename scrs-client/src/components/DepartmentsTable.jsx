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
    <div className="overflow-hidden border rounded-lg shadow-md">
      <Table className="min-w-full divide-y">
        <TableHeader className="font-bold">
          <TableRow>
            <TableCell className="px-4 py-2">ID</TableCell>
            <TableCell className="px-4 py-2">Name</TableCell>
            <TableCell className="px-4 py-2">SN</TableCell>
            <TableCell className="px-4 py-2">HOD</TableCell>
            <TableCell className="px-4 py-2">Actions</TableCell>
          </TableRow>
        </TableHeader>
        <TableBody>
          {departments.map((dept, index) => {
            const dynamicIndex = (currentPage - 1) * itemsPerPage + index + 1;
            return (
              <TableRow
                key={dept.id}
                className="font-medium transition-colors duration-200 hover:bg-gray-100"
              >
                <TableCell className="px-4 py-2">{dynamicIndex}</TableCell>
                <TableCell className="px-4 py-2">{dept.deptName}</TableCell>
                <TableCell className="px-4 py-2">{dept.sn}</TableCell>
                <TableCell className="px-4 py-2">
                  {dept.hod == null ? "Didn't set yet" : dept.hod.name}
                </TableCell>
                <TableCell className="px-4 py-2">
                  <div className="flex space-x-2">
                    <Button
                      onClick={() => onEdit(dept)}
                      className="flex items-center px-3 py-2 space-x-1 text-white transition-all duration-150 ease-in-out rounded-lg hover:bg-gray-600"
                    >
                      <Pencil className="w-4 h-4" />
                      <span>Edit</span>
                    </Button>
                    <Button
                      onClick={() => onDelete(dept.id)}
                      className="flex items-center px-3 py-2 space-x-1 text-white transition-all duration-150 ease-in-out bg-red-500 rounded-lg hover:bg-red-600"
                    >
                      <Trash2 className="w-4 h-4" />
                      <span>Delete</span>
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
