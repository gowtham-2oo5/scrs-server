import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Eye, Pencil, Trash } from "lucide-react";

export function TemplateCard({ template, onView, onEdit, onDelete }) {
  return (
    <Card>
      <CardHeader>
        <CardTitle>{template.title}</CardTitle>
      </CardHeader>
      <CardContent>
        <p className="text-sm text-muted-foreground">{template.description}</p>
        <p className="mt-2 text-xs text-muted-foreground">
          Last modified: {template.lastModified}
        </p>
      </CardContent>
      <CardFooter className="flex justify-end space-x-2">
        <Button variant="outline" size="icon" onClick={() => onView(template)}>
          <Eye className="w-4 h-4" />
        </Button>
        <Button variant="outline" size="icon" onClick={() => onEdit(template)}>
          <Pencil className="w-4 h-4" />
        </Button>
        <Button
          variant="outline"
          size="icon"
          onClick={() => onDelete(template)}
        >
          <Trash className="w-4 h-4" />
        </Button>
      </CardFooter>
    </Card>
  );
}
