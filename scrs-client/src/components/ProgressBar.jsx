import React from 'react'
import { Progress } from "@/components/ui/progress"

export function ProgressBar({ courseCategories }) {
  const totalSlots = courseCategories.reduce((acc, course) => acc + course.maxSlots, 0)
  const usedSlots = courseCategories.reduce((acc, course) => acc + course.currentSlots, 0)
  const progressPercentage = (usedSlots / totalSlots) * 100

  return (
    <div className="w-full mt-4">
      <Progress value={progressPercentage} className="w-full" />
      <div className="mt-2 text-sm text-center">
        Schedule Progress: {usedSlots} / {totalSlots} slots filled ({progressPercentage.toFixed(1)}%)
      </div>
    </div>
  )
}

