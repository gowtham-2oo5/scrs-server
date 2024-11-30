import { useEffect, useState } from "react";
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
import { getAllFaculties, getFacultiesByDept } from "@/api/faculty";
import { addSingleDept, setHodForDept } from "@/api/dept";
import { useToast } from "@/hooks/use-toast";
import { Loader2 } from "lucide-react";

export default function AddDepartmentForm({
  onAddDepartment,
  isEdit = false,
  initialDepartment = { name: "", sn: "", hodId: "" },
}) {
  const [newDepartment, setNewDepartment] = useState(initialDepartment);
  const [hodOptions, setHodOptions] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const { toast } = useToast();

  useEffect(() => {
    const fetchFaculties = async () => {
      setIsLoading(true);
      try {
        const res = isEdit
          ? await getFacultiesByDept(initialDepartment.sn)
          : await getAllFaculties();
        setHodOptions(
          res.data.map((faculty) => ({
            value: faculty.empId,
            label: faculty.name,
          }))
        );
      } catch (error) {
        console.error(`Error fetching faculties: ${error.message}`);
        toast({
          title: "Error fetching faculties",
          description: error.message,
          variant: "destructive",
        });
      } finally {
        setIsLoading(false);
      }
    };

    fetchFaculties();
  }, [isEdit, initialDepartment.sn, toast]);

  const handleInputChange = (e) => {
    const { id, value } = e.target;
    setNewDepartment((prev) => ({ ...prev, [id]: value }));
  };

  const handleHodChange = (value) => {
    setNewDepartment((prev) => ({ ...prev, hodId: value }));
  };

  const handleSubmit = async () => {
    setIsLoading(true);
    try {
      if (!isEdit) {
        const res = await addSingleDept(newDepartment);
        onAddDepartment(res);
        if (res.status === 200) {
          setNewDepartment({ id: null, name: "", sn: "", hodId: "" });
          toast({
            title: "Department added successfully",
            description: `${newDepartment.name} has been added.`,
          });
        }
      } else {
        const res = await setHodForDept(newDepartment.sn, newDepartment.hodId);
        if (res.status === 200) {
          onAddDepartment(res);
          toast({
            title: "HOD updated successfully",
            description: `HOD for ${newDepartment.name} has been updated.`,
          });
        } else {
          throw new Error(res.error);
        }
      }
    } catch (error) {
      console.error(
        `Error ${isEdit ? "updating" : "adding"} department: ${error.message}`
      );
      toast({
        title: `Error ${isEdit ? "updating" : "adding"} department`,
        description: error.message,
        variant: "destructive",
      });
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="grid gap-6 py-4">
      {!isEdit && (
        <div className="grid items-center grid-cols-4 gap-4">
          <Label
            htmlFor="name"
            className="font-semibold text-right text-gray-800"
          >
            Name
          </Label>
          <Input
            id="name"
            value={newDepartment.name}
            onChange={handleInputChange}
            className="col-span-3 border-gray-300 rounded-lg focus:border-gray-500"
          />
        </div>
      )}
      <div className="grid items-center grid-cols-4 gap-4">
        <Label htmlFor="sn" className="font-semibold text-right text-gray-800">
          SN
        </Label>
        <Input
          id="sn"
          value={newDepartment.sn}
          onChange={handleInputChange}
          className="col-span-3 border-gray-300 rounded-lg focus:border-gray-500"
        />
      </div>
      <div className="grid items-center grid-cols-4 gap-4">
        <Label htmlFor="hod" className="font-semibold text-right text-gray-800">
          HOD
        </Label>
        <Select onValueChange={handleHodChange} value={newDepartment.hodId}>
          <SelectTrigger className="col-span-3 border-gray-300 rounded-lg focus:border-gray-500">
            <SelectValue placeholder="Select HOD" />
          </SelectTrigger>
          <SelectContent className="border-gray-200 bg-gray-50">
            {hodOptions.map((option) => (
              <SelectItem
                key={option.value}
                value={option.value}
                className="text-gray-800 hover:bg-gray-100"
              >
                {option.label}
              </SelectItem>
            ))}
          </SelectContent>
        </Select>
      </div>
      <Button
        onClick={handleSubmit}
        className="px-6 py-2 font-semibold text-white transition duration-300 ease-in-out bg-gray-600 rounded-lg shadow-md hover:bg-gray-700"
        disabled={isLoading}
      >
        {isLoading ? (
          <>
            <Loader2 className="w-4 h-4 mr-2 animate-spin" />
            Processing...
          </>
        ) : isEdit ? (
          "Update Department"
        ) : (
          "Add Department"
        )}
      </Button>
    </div>
  );
}
