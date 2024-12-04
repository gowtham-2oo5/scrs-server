import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";

export default function FacultyModal({ faculty, onClose }) {
  if (!faculty) return null;
  console.log("In facModal", faculty);

  return (
    <Dialog open={!!faculty} onOpenChange={onClose}>
      <DialogContent className="border-2 shadow-lg rounded-xl">
        <DialogHeader>
          <DialogTitle className="text-2xl font-bold">
            Faculty Details
          </DialogTitle>
        </DialogHeader>
        <div className="flex flex-col items-center space-y-4">
          <img
            src={faculty.profilePicture}
            alt={`${faculty.name}'s profile`}
            width={100}
            height={100}
            className="rounded-full"
          />
          <h2 className="text-xl font-semibold">{faculty.name}</h2>
          <p className="text-gray-600">{faculty.designation}</p>
          <div className="grid grid-cols-2 gap-4 text-sm">
            <div>
              <p className="font-semibold">Emp ID:</p>
              <p>{faculty.empId}</p>
            </div>
            <div>
              <p className="font-semibold">Department:</p>
              <p>{faculty.department}</p>
            </div>
            <div>
              <p className="font-semibold">Email:</p>
              <p>{faculty.email}</p>
            </div>
          </div>
        </div>
      </DialogContent>
    </Dialog>
  );
}
