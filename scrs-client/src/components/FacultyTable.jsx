import { useState, useMemo } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Pencil, Trash2, Info } from "lucide-react";

const designationOrder = {
  HOD: 1,
  CC: 2,
  Professor: 3,
};

export default function FacultyTable({
  faculty,
  onEdit,
  onDelete,
  onLearnMore,
  currentPage,
  itemsPerPage,
}) {
  const [searchTerm, setSearchTerm] = useState("");
  const [departmentFilter, setDepartmentFilter] = useState("all");

  const departments = useMemo(() => {
    return ["all", ...new Set(faculty.map((fac) => fac.department))];
  }, [faculty]);

  const filteredAndSortedFaculty = useMemo(() => {
    return faculty
      .filter(
        (fac) =>
          (fac.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
            fac.email.toLowerCase().includes(searchTerm.toLowerCase())) &&
          (departmentFilter === "all" || fac.department === departmentFilter)
      )
      .sort((a, b) => {
        const orderA = designationOrder[a.designation] || Infinity;
        const orderB = designationOrder[b.designation] || Infinity;
        return orderA - orderB;
      });
  }, [faculty, searchTerm, departmentFilter]);

  return (
    <div className="space-y-4">
      <div className="flex flex-col space-y-2 sm:flex-row sm:space-y-0 sm:space-x-4">
        <Input
          placeholder="Search by name or email"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="w-full sm:w-64"
        />
        <Select
          value={departmentFilter}
          onValueChange={(value) => setDepartmentFilter(value)}
        >
          <SelectTrigger className="w-full sm:w-[180px]">
            <SelectValue placeholder="Filter by Department" />
          </SelectTrigger>
          <SelectContent>
            {departments.map((dept) => (
              <SelectItem key={dept} value={dept}>
                {dept === "all" ? "All Departments" : dept}
              </SelectItem>
            ))}
          </SelectContent>
        </Select>
      </div>
      <div className="overflow-x-auto border rounded-lg shadow-md">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead className="w-[100px]">Profile</TableHead>
              <TableHead>Emp ID</TableHead>
              <TableHead>Name</TableHead>
              <TableHead>Email</TableHead>
              <TableHead>Designation</TableHead>
              <TableHead>Department</TableHead>
              <TableHead className="text-right">Actions</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {filteredAndSortedFaculty.length === 0 ? (
              <TableRow>
                <TableCell colSpan={7} className="h-24 text-center">
                  No results.
                </TableCell>
              </TableRow>
            ) : (
              filteredAndSortedFaculty.map((fac, ind) => (
                <TableRow key={ind}>
                  <TableCell className="font-medium">
                    <img
                      src={fac.profilePicture || "/placeholder.svg"}
                      alt={`${fac.name}'s profile`}
                      width={40}
                      height={40}
                      className="rounded-full"
                    />
                  </TableCell>
                  <TableCell>{fac.empId}</TableCell>
                  <TableCell>{fac.name}</TableCell>
                  <TableCell>{fac.email}</TableCell>
                  <TableCell>{fac.designation}</TableCell>
                  <TableCell>{fac.department}</TableCell>
                  <TableCell className="text-right">
                    <div className="flex justify-end space-x-2">
                      <Button
                        onClick={() => onEdit(fac)}
                        size="icon"
                        variant="ghost"
                      >
                        <Pencil className="w-4 h-4" />
                        <span className="sr-only">Edit</span>
                      </Button>
                      <Button
                        onClick={() => onDelete(fac.id)}
                        size="icon"
                        variant="ghost"
                      >
                        <Trash2 className="w-4 h-4" />
                        <span className="sr-only">Delete</span>
                      </Button>
                      <Button
                        onClick={() => onLearnMore(fac)}
                        size="icon"
                        variant="ghost"
                      >
                        <Info className="w-4 h-4" />
                        <span className="sr-only">Learn More</span>
                      </Button>
                    </div>
                  </TableCell>
                </TableRow>
              ))
            )}
          </TableBody>
        </Table>
      </div>
    </div>
  );
}
