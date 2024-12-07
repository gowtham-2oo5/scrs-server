import { useState, useCallback, useEffect } from "react";
import { fetchCourseCategoriesWithColors } from "@/utils/clusterTemplateUtils";

export function useSchedule() {
  const [courseCategories, setCourseCategories] = useState([]);
  const [schedule, setSchedule] = useState({});
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const categories = await fetchCourseCategoriesWithColors();
        setCourseCategories(categories);
      } catch (error) {
        console.error("Failed to fetch course categories:", error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchCategories();
  }, []);

  const moveCourse = useCallback(
    (courseId, day, timeSlot) => {
      const courseCategory = courseCategories.find(
        (c) => c.id.toString() === courseId
      );
      if (
        courseCategory &&
        courseCategory.currentSlots < courseCategory.maxSlots
      ) {
        setSchedule((prev) => ({
          ...prev,
          [day]: {
            ...prev[day],
            [timeSlot]: courseId,
          },
        }));
        setCourseCategories((prev) =>
          prev.map((c) =>
            c.id.toString() === courseId
              ? { ...c, currentSlots: c.currentSlots + 1 }
              : c
          )
        );
      }
    },
    [courseCategories]
  );

  const removeCourse = useCallback(
    (day, timeSlot) => {
      const courseCategoryId = schedule[day]?.[timeSlot];
      if (courseCategoryId) {
        setSchedule((prev) => ({
          ...prev,
          [day]: {
            ...prev[day],
            [timeSlot]: null,
          },
        }));
        setCourseCategories((prev) =>
          prev.map((c) =>
            c.id.toString() === courseCategoryId
              ? { ...c, currentSlots: c.currentSlots - 1 }
              : c
          )
        );
      }
    },
    [schedule]
  );

  return { courseCategories, schedule, moveCourse, removeCourse, isLoading };
}
