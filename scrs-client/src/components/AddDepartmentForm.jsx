import { useState } from "react";
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
import { addSingleDept } from "@/api/index";

export default function AddDepartmentForm({
  onAddDepartment,
  isEdit = false,
  initialDepartment = { name: "", sn: "", hod: "" },
}) {
  const [newDepartment, setNewDepartment] = useState(initialDepartment);

  const hodOptions = [];

  const handleInputChange = (e) => {
    const { id, value } = e.target;
    setNewDepartment((prev) => ({ ...prev, [id]: value }));
  };

  const handleHodChange = (value) => {
    setNewDepartment((prev) => ({ ...prev, hod: value }));
  };

  const handleSubmit = () => {
    console.log(newDepartment);
    onAddDepartment(newDepartment);
    // addSingleDept(newDepartment);
    setNewDepartment({ id: null, name: "", sn: "", hod: "" });
  };

  return (
    <div className="grid gap-6 py-4">
      <div className="grid items-center grid-cols-4 gap-4">
        <Label
          htmlFor="name"
          className="font-semibold text-right text-blue-800"
        >
          Name
        </Label>
        <Input
          id="name"
          value={newDepartment.name}
          onChange={handleInputChange}
          className="col-span-3 border-blue-300 rounded-lg focus:border-blue-500"
        />
      </div>
      <div className="grid items-center grid-cols-4 gap-4">
        <Label htmlFor="sn" className="font-semibold text-right text-blue-800">
          SN
        </Label>
        <Input
          id="sn"
          value={newDepartment.sn}
          onChange={handleInputChange}
          className="col-span-3 border-blue-300 rounded-lg focus:border-blue-500"
        />
      </div>
      <div className="grid items-center grid-cols-4 gap-4">
        <Label htmlFor="hod" className="font-semibold text-right text-blue-800">
          HOD
        </Label>
        <Select onValueChange={handleHodChange} value={newDepartment.hod}>
          <SelectTrigger className="col-span-3 border-blue-300 rounded-lg focus:border-blue-500">
            <SelectValue placeholder="Select HOD" />
          </SelectTrigger>
          <SelectContent className="border-blue-200 bg-blue-50">
            {hodOptions.map((option) => (
              <SelectItem
                key={option.value}
                value={option.value}
                className="text-blue-800 hover:bg-blue-100"
              >
                {option.label}
              </SelectItem>
            ))}
          </SelectContent>
        </Select>
      </div>
      <Button
        onClick={handleSubmit}
        className="px-6 py-2 font-semibold text-white transition duration-300 ease-in-out bg-blue-600 rounded-lg shadow-md hover:bg-blue-700"
      >
        {isEdit ? "Update Department" : "Add Department"}
      </Button>
    </div>
  );
}
