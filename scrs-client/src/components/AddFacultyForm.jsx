import { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

export default function AddFacultyForm({
  onAddFaculty,
  isEdit = false,
  initialFaculty = {},
}) {
  const [faculty, setFaculty] = useState({
    empId: "",
    name: "",
    contact: "",
    email: "",
    designation: "",
    exp: "",
    department: "",
    ...initialFaculty,
  });

  useEffect(() => {
    if (isEdit && initialFaculty) {
      setFaculty(initialFaculty);
    }
  }, [isEdit, initialFaculty]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFaculty((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onAddFaculty(faculty);
    setFaculty({
      empId: "",
      name: "",
      contact: "",
      email: "",
      designation: "",
      exp: "",
      department: "",
    });
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div>
        <Label htmlFor="empId">Employee ID</Label>
        <Input
          id="empId"
          name="empId"
          value={faculty.empId}
          onChange={handleChange}
          required
        />
      </div>
      <div>
        <Label htmlFor="name">Name</Label>
        <Input
          id="name"
          name="name"
          value={faculty.name}
          onChange={handleChange}
          required
        />
      </div>
      <div>
        <Label htmlFor="contact">Contact</Label>
        <Input
          id="contact"
          name="contact"
          value={faculty.contact}
          onChange={handleChange}
          required
        />
      </div>
      <div>
        <Label htmlFor="email">Email</Label>
        <Input
          id="email"
          name="email"
          type="email"
          value={faculty.email}
          onChange={handleChange}
          required
        />
      </div>
      <div>
        <Label htmlFor="designation">Designation</Label>
        <Select
          name="designation"
          value={faculty.designation}
          onValueChange={(value) =>
            setFaculty((prev) => ({ ...prev, designation: value }))
          }
        >
          <SelectTrigger>
            <SelectValue placeholder="Select designation" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="Professor">Professor</SelectItem>
            <SelectItem value="Associate Professor">
              Associate Professor
            </SelectItem>
            <SelectItem value="Assistant Professor">
              Assistant Professor
            </SelectItem>
          </SelectContent>
        </Select>
      </div>
      <div>
        <Label htmlFor="exp">Experience (years)</Label>
        <Input
          id="exp"
          name="exp"
          type="number"
          value={faculty.exp}
          onChange={handleChange}
          required
        />
      </div>
      <div>
        <Label htmlFor="department">Department</Label>
        <Input
          id="department"
          name="department"
          value={faculty.department}
          onChange={handleChange}
          required
        />
      </div>
      <Button type="submit" className="w-full">
        {isEdit ? "Update Faculty" : "Add Faculty"}
      </Button>
    </form>
  );
}
