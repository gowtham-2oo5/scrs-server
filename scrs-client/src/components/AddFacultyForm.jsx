import { useEffect, useState } from "react";
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
import { Calendar } from "@/components/ui/calendar";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { format } from "date-fns";
import { CalendarIcon, Upload, X } from "lucide-react";
import { getDepts } from "@/api/dept";

export default function AddFacultyForm({
  onAddFaculty,
  isEdit = false,
  initialFaculty = {},
}) {
  const { register, handleSubmit, control, setValue, watch } = useForm({
    defaultValues: {
      name: "",
      contact: "",
      mail: "",
      empId: "",
      dept: "",
      dob: null,
      designation: "",
      joined_at: null,
      exp: "",
      profilePicture: null,
      ...initialFaculty,
    },
  });

  const profilePicture = watch("profilePicture");
  const [deptOptions, setDeptOptions] = useState([]);

  useEffect(() => {
    const fetchDepartments = async () => {
      try {
        const response = await getDepts();
        const data = response.data;
        setDeptOptions(
          data.map((dept) => ({ value: dept.sn, label: dept.deptName }))
        );
      } catch (error) {
        console.error("Error fetching departments:", error);
      }
    };

    fetchDepartments();
  }, []);

  useEffect(() => {
    if (isEdit && initialFaculty) {
      Object.keys(initialFaculty).forEach((key) => {
        setValue(key, initialFaculty[key]);
      });
    }
  }, [isEdit, initialFaculty, setValue]);

  const onSubmit = async (data) => {
    const formData = new FormData();

    // Append faculty details as JSON
    const facultyDetailsJson = JSON.stringify({
      name: data.name,
      contact: data.contact,
      mail: data.mail,
      empId: data.empId,
      dept: data.dept,
      dob: data.dob ? format(data.dob, "yyyy-MM-dd") : null,
      designation: data.designation,
      joined_at: data.joined_at ? format(data.joined_at, "yyyy-MM-dd") : null,
      exp: data.exp,
    });
    formData.append("facDetails", facultyDetailsJson);

    // Append profile picture if it exists
    if (data.profilePicture && data.profilePicture[0]) {
      formData.append("profilePicture", data.profilePicture[0]);
    }

    await onAddFaculty(formData);
  };

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
      <div className="grid grid-cols-2 gap-4">
        <div>
          <Label htmlFor="name">Name</Label>
          <Input id="name" {...register("name", { required: true })} />
        </div>
        <div>
          <Label htmlFor="contact">Contact</Label>
          <Input id="contact" {...register("contact", { required: true })} />
        </div>
        <div>
          <Label htmlFor="mail">Email</Label>
          <Input
            id="mail"
            type="email"
            {...register("mail", { required: true })}
          />
        </div>
        <div>
          <Label htmlFor="empId">Employee ID</Label>
          <Input id="empId" {...register("empId", { required: true })} />
        </div>
        <div>
          <Label htmlFor="dept">Department</Label>
          <Controller
            name="dept"
            control={control}
            rules={{ required: true }}
            render={({ field }) => (
              <Select onValueChange={field.onChange} value={field.value}>
                <SelectTrigger>
                  <SelectValue placeholder="Select department" />
                </SelectTrigger>
                <SelectContent>
                  {deptOptions.map((option) => (
                    <SelectItem key={option.value} value={option.value}>
                      {option.label}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            )}
          />
        </div>

        <div>
          <Label htmlFor="dob">Date of Birth</Label>
          <Controller
            name="dob"
            control={control}
            render={({ field }) => (
              <Popover>
                <PopoverTrigger asChild>
                  <Button
                    variant="outline"
                    className={`w-full justify-start text-left font-normal ${
                      !field.value && "text-muted-foreground"
                    }`}
                  >
                    <CalendarIcon className="w-4 h-4 mr-2" />
                    {field.value ? format(field.value, "PPP") : "Pick a date"}
                  </Button>
                </PopoverTrigger>
                <PopoverContent className="w-auto p-0" align="start">
                  <Calendar
                    mode="single"
                    selected={field.value}
                    onSelect={field.onChange}
                    initialFocus
                  />
                </PopoverContent>
              </Popover>
            )}
          />
        </div>
        <div>
          <Label htmlFor="designation">Designation</Label>
          <Controller
            name="designation"
            control={control}
            rules={{ required: true }}
            render={({ field }) => (
              <Select onValueChange={field.onChange} value={field.value}>
                <SelectTrigger>
                  <SelectValue placeholder="Select designation" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="HOD">HOD</SelectItem>
                  <SelectItem value="CC">CC</SelectItem>
                  <SelectItem value="Professor">Professor</SelectItem>
                </SelectContent>
              </Select>
            )}
          />
        </div>
        <div>
          <Label htmlFor="joined_at">Joined At</Label>
          <Controller
            name="joined_at"
            control={control}
            render={({ field }) => (
              <Popover>
                <PopoverTrigger asChild>
                  <Button
                    variant="outline"
                    className={`w-full justify-start text-left font-normal ${
                      !field.value && "text-muted-foreground"
                    }`}
                  >
                    <CalendarIcon className="w-4 h-4 mr-2" />
                    {field.value ? format(field.value, "PPP") : "Pick a date"}
                  </Button>
                </PopoverTrigger>
                <PopoverContent className="w-auto p-0" align="start">
                  <Calendar
                    mode="single"
                    selected={field.value}
                    onSelect={field.onChange}
                    initialFocus
                  />
                </PopoverContent>
              </Popover>
            )}
          />
        </div>
        <div>
          <Label htmlFor="exp">Experience (years)</Label>
          <Input
            id="exp"
            type="number"
            {...register("exp", { required: true })}
          />
        </div>
        <div>
          <Label htmlFor="profilePicture">Profile Picture</Label>
          <div className="flex items-center space-x-2">
            <Input
              id="profilePicture"
              type="file"
              accept="image/*"
              {...register("profilePicture")}
              className="hidden"
            />
            <Button
              type="button"
              variant="outline"
              onClick={() => document.getElementById("profilePicture").click()}
            >
              <Upload className="w-4 h-4 mr-2" />
              Upload Picture
            </Button>
            {profilePicture && profilePicture[0] ? (
              <img
                src={URL.createObjectURL(profilePicture[0])}
                alt="Profile Preview"
                className="object-cover w-10 h-10 rounded-full"
              />
            ) : (
              <X className="w-4 h-4 mr-2" />
            )}
          </div>
        </div>
      </div>
      <Button type="submit" className="w-full">
        {isEdit ? "Update Faculty" : "Add Faculty"}
      </Button>
    </form>
  );
}
