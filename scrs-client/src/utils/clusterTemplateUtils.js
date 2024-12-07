import { getAllCourseCategories } from "@/api/course_categories";

export const DAYS = [
  "MONDAY",
  "TUESDAY",
  "WEDNESDAY",
  "THURSDAY",
  "FRIDAY",
  "SATURDAY",
];

export const TIME_SLOTS = [
  "HOUR_1",
  "HOUR_2",
  "HOUR_3",
  "HOUR_4",
  "HOUR_5",
  "HOUR_6",
  "HOUR_7",
  "HOUR_8",
];

const getRandomColor = () => {
  const colors = [
    "#FEE2E2",
    "#E0F2FE",
    "#FEF3C7",
    "#DCFCE7",
    "#EDE9FE",
    "#FAE8E8",
    "#D1FAE5",
    "#FFF7ED",
  ];
  return colors[Math.floor(Math.random() * colors.length)];
};

export const fetchCourseCategoriesWithColors = async () => {
  try {
    const categories = await getAllCourseCategories();

    const actualRes = categories.data
      .filter((cat) => cat.title !== null && cat.maxSessionsPerWeek > 0)
      .map((cat) => ({
        id: cat.id,
        name: cat.title,
        minSlots: cat.minSessionsPerWeek,
        maxSlots: cat.maxSessionsPerWeek,
      }))
      .sort((a, b) => a.name.localeCompare(b.name));

    console.log(`In utils: ${JSON.stringify(actualRes)}`);

    return actualRes.map((category) => ({
      ...category,
      color: getRandomColor(),
      currentSlots: 0,
    }));
  } catch (error) {
    console.error("Failed to fetch course categories:", error);
    throw error;
  }
};

export const ItemTypes = {
  COURSE: "course",
};
