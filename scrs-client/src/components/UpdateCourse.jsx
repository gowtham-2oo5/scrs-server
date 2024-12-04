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
import {
  updateCourseTitle,
  updateLTPS,
  updateCourseIncharge,
} from "@/api/courses";

export default function UpdateCourse({
  course,
  departments,
  onSuccess,
  onCancel,
}) {
  const { control, handleSubmit } = useForm({
    defaultValues: {
      id: course.id,
      courseTitle: course.courseTitle,
      courseCode: course.courseCode,
      courseDesc: course.courseDesc,
      courseDept: course.courseDept,
      courseIncharge: course.courseIncharge,
      credits: course.credits,
      L: course.L,
      T: course.T,
      P: course.P,
      S: course.S,
    },
  });

  const onSubmit = async (data) => {
    try {
      const titleResponse = await updateCourseTitle(data.id, data.courseTitle);
      const ltpsResponse = await updateLTPS(
        data.id,
        data.L,
        data.T,
        data.P,
        data.S
      );
      const inchargeResponse = await updateCourseIncharge(
        data.id,
        data.courseIncharge
      );

      if (titleResponse.data && ltpsResponse.data && inchargeResponse.data) {
        onSuccess({
          ...data,
          ...titleResponse.data,
          ...ltpsResponse.data,
          ...inchargeResponse.data,
        });
      } else {
        console.error(
          "Error updating course:",
          titleResponse.error || ltpsResponse.error || inchargeResponse.error
        );
      }
    } catch (error) {
      console.error("Error updating course:", error);
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
            <Input
              {...field}
              disabled
              className="col-span-3 bg-gray-100 rounded-lg"
            />
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
            <Select onValueChange={field.onChange} value={field.value} disabled>
              <SelectTrigger className="col-span-3 bg-gray-100 rounded-lg">
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
            <Input
              {...field}
              type="number"
              disabled
              className="col-span-3 bg-gray-100 rounded-lg"
            />
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
          Update Course
        </Button>
      </div>
    </form>
  );
}
