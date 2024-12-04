import { useForm, Controller } from "react-hook-form";
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
import { createCourse } from "@/api/courses";

export default function AddCourse({ departments, onSuccess, onCancel }) {
  const { control, handleSubmit, reset } = useForm({
    defaultValues: {
      courseTitle: "",
      courseCode: "",
      courseDesc: "",
      courseDept: "",
      courseIncharge: "",
      credits: "",
      L: "",
      T: "",
      P: "",
      S: "",
    },
  });

  const onSubmit = async (data) => {
    try {
      const response = await createCourse(data);
      if (response.data) {
        onSuccess(response.data);
        reset();
      } else {
        console.error("Error creating course:", response.error);
      }
    } catch (error) {
      console.error("Error creating course:", error);
    }
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="grid gap-6 py-4">
      <div className="grid items-center grid-cols-4 gap-4">
        <Label htmlFor="courseTitle" className="font-semibold text-right">
          Title
        </Label>
        <Controller
          name="courseTitle"
          control={control}
          rules={{ required: "Title is required" }}
          render={({ field }) => (
            <Input {...field} className="col-span-3 rounded-lg" />
          )}
        />
      </div>
      <div className="grid items-center grid-cols-4 gap-4">
        <Label htmlFor="courseCode" className="font-semibold text-right">
          Course Code
        </Label>
        <Controller
          name="courseCode"
          control={control}
          rules={{ required: "Course Code is required" }}
          render={({ field }) => (
            <Input {...field} className="col-span-3 rounded-lg" />
          )}
        />
      </div>
      <div className="grid items-center grid-cols-4 gap-4">
        <Label htmlFor="courseDesc" className="font-semibold text-right">
          Description
        </Label>
        <Controller
          name="courseDesc"
          control={control}
          render={({ field }) => (
            <Input {...field} className="col-span-3 rounded-lg" />
          )}
        />
      </div>
      <div className="grid items-center grid-cols-4 gap-4">
        <Label htmlFor="courseDept" className="font-semibold text-right">
          Offering Department
        </Label>
        <Controller
          name="courseDept"
          control={control}
          rules={{ required: "Department is required" }}
          render={({ field }) => (
            <Select onValueChange={field.onChange} value={field.value}>
              <SelectTrigger className="col-span-3 rounded-lg">
                <SelectValue placeholder="Select Department" />
              </SelectTrigger>
              <SelectContent>
                {departments.map((dept) => (
                  <SelectItem key={dept.id} value={dept.id}>
                    {dept.name}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          )}
        />
      </div>
      <div className="grid items-center grid-cols-4 gap-4">
        <Label htmlFor="courseIncharge" className="font-semibold text-right">
          Course Incharge
        </Label>
        <Controller
          name="courseIncharge"
          control={control}
          rules={{ required: "Course Incharge is required" }}
          render={({ field }) => (
            <Input {...field} className="col-span-3 rounded-lg" />
          )}
        />
      </div>
      <div className="grid items-center grid-cols-4 gap-4">
        <Label htmlFor="credits" className="font-semibold text-right">
          Credits
        </Label>
        <Controller
          name="credits"
          control={control}
          rules={{ required: "Credits are required" }}
          render={({ field }) => (
            <Input {...field} type="number" className="col-span-3 rounded-lg" />
          )}
        />
      </div>
      <div className="grid items-center grid-cols-4 gap-4">
        <Label className="font-semibold text-right">L-T-P-S</Label>
        <div className="grid grid-cols-4 col-span-3 gap-2">
          <Controller
            name="L"
            control={control}
            render={({ field }) => (
              <Input
                {...field}
                type="number"
                placeholder="L"
                className="rounded-lg"
              />
            )}
          />
          <Controller
            name="T"
            control={control}
            render={({ field }) => (
              <Input
                {...field}
                type="number"
                placeholder="T"
                className="rounded-lg"
              />
            )}
          />
          <Controller
            name="P"
            control={control}
            render={({ field }) => (
              <Input
                {...field}
                type="number"
                placeholder="P"
                className="rounded-lg"
              />
            )}
          />
          <Controller
            name="S"
            control={control}
            render={({ field }) => (
              <Input
                {...field}
                type="number"
                placeholder="S"
                className="rounded-lg"
              />
            )}
          />
        </div>
      </div>
      <div className="flex justify-end space-x-2">
        <Button
          type="button"
          variant="outline"
          onClick={onCancel}
          className="px-4 py-2 rounded-lg"
        >
          Cancel
        </Button>
        <Button
          type="submit"
          className="px-4 py-2 font-semibold transition duration-300 ease-in-out rounded-lg shadow-md"
        >
          Add Course
        </Button>
      </div>
    </form>
  );
}
