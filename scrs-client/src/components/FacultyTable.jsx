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
import { Pencil, Trash2, Info, ChevronLeft, ChevronRight } from "lucide-react";

const designationOrder = {
  HOD: 1,
  CC: 2,
  Professor: 3,
};

export default function FacultyTable({
  faculty = [],
  onEdit,
  onDelete,
  onLearnMore,
}) {
  const [searchTerm, setSearchTerm] = useState("");
  const [departmentFilter, setDepartmentFilter] = useState("all");
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 5;

  // Extract unique departments for the filter dropdown
  const departments = useMemo(() => {
    return ["all", ...new Set(faculty.map((fac) => fac.department))];
  }, [faculty]);

  // Filter and sort faculty based on search term, department, and designation order
  const filteredAndSortedFaculty = useMemo(() => {
    return faculty
      .filter((fac) => {
        const matchesSearch =
          fac.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
          fac.email.toLowerCase().includes(searchTerm.toLowerCase());
        const matchesDepartment =
          departmentFilter === "all" || fac.department === departmentFilter;
        return matchesSearch && matchesDepartment;
      })
      .sort((a, b) => {
        const orderA = designationOrder[a.designation] || Infinity;
        const orderB = designationOrder[b.designation] || Infinity;
        return orderA - orderB;
      });
  }, [faculty, searchTerm, departmentFilter]);

  // Calculate pagination
  const totalPages = Math.ceil(filteredAndSortedFaculty.length / itemsPerPage);
  const paginatedFaculty = filteredAndSortedFaculty.slice(
    (currentPage - 1) * itemsPerPage,
    currentPage * itemsPerPage
  );

  return (
    <div className="space-y-4">
      {/* Filters: Search and Department */}
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

      {/* Faculty Table */}
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
            {paginatedFaculty.length === 0 ? (
              <TableRow>
                <TableCell colSpan={7} className="h-24 text-center">
                  No results found.
                </TableCell>
              </TableRow>
            ) : (
              paginatedFaculty.map((fac) => (
                <TableRow key={fac.id}>
                  <TableCell>
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

      {/* Pagination */}
      <div className="flex items-center justify-between mt-4">
        <Button
          onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 1))}
          disabled={currentPage === 1}
          className="px-4 py-2 font-semibold text-white transition duration-300 ease-in-out rounded-lg shadow-md hover:bg-gray-600"
        >
          <ChevronLeft className="w-4 h-4 mr-2" />
          Previous
        </Button>
        <span>
          Page {currentPage} of {totalPages}
        </span>
        <Button
          onClick={() =>
            setCurrentPage((prev) => Math.min(prev + 1, totalPages))
          }
          disabled={currentPage === totalPages}
          className="px-4 py-2 font-semibold text-white transition duration-300 ease-in-out rounded-lg shadow-md hover:bg-gray-600"
        >
          Next
          <ChevronRight className="w-4 h-4 ml-2" />
        </Button>
      </div>
    </div>
  );
}
