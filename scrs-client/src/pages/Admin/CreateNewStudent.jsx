'use client'

import { useState } from 'react'
import { useForm, Controller } from 'react-hook-form'
import { AlertCircle, CheckCircle, Calendar as CalendarIcon } from "lucide-react"
import { format } from "date-fns"

import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"
import { Alert, AlertDescription, AlertTitle } from "@/components/ui/alert"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Progress } from "@/components/ui/progress"
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover"
import { Calendar } from "@/components/ui/calendar"

// Mock custom hook for demonstration
const useCreateStudent = () => {
    const [loading, setLoading] = useState(false)
    const [success, setSuccess] = useState(false)

    const handleCreateStudent = async (formData) => {
        setLoading(true)
        // Simulating API call
        await new Promise(resolve => setTimeout(resolve, 2000))
        setLoading(false)
        setSuccess(true)
    }

    return { handleCreateStudent, loading, success }
}

const steps = [
    { id: 'personal', title: 'Personal Information' },
    { id: 'account', title: 'Account Information' },
    { id: 'academic', title: 'Academic Information' },
]

export default function CreateNewStudent() {
    const [currentStep, setCurrentStep] = useState(0)
    const { register, handleSubmit, control, formState: { errors }, trigger } = useForm({
        defaultValues: {
            name: '',
            email: '',
            contact: '',
            profilePicture: null,
            username: '',
            password: '',
            regNum: '',
            specialization: '',
            joinedAt: '',
            year: '',
            semester: '',
        }
    })
    const [error, setError] = useState(null)
    const { handleCreateStudent, loading, success } = useCreateStudent()

    const onSubmit = async (data) => {
        try {
            const formData = new FormData()
            formData.append('profilePicture', data.profilePicture[0])
            formData.append('studentDetails', JSON.stringify({
                ...data,
                profilePicture: undefined,
            }))
            await handleCreateStudent(formData)
        } catch (err) {
            console.error('Error creating student:', err)
            setError(err.message || 'An error occurred while creating the student.')
        }
    }

    const nextStep = async () => {
        const fields = steps[currentStep].id === 'personal'
            ? ['name', 'email', 'contact']
            : steps[currentStep].id === 'account'
                ? ['username', 'password']
                : ['regNum', 'specialization', 'joinedAt', 'year', 'semester']

        const isStepValid = await trigger(fields)
        if (isStepValid) setCurrentStep(prev => prev + 1)
    }

    const prevStep = () => setCurrentStep(prev => prev - 1)

    return (
        <div className='min-h-screen flex items-center justify-center bg-gray-100 p-4'>
            <Card className="w-full max-w-4xl mx-auto">
                <CardHeader>
                    <CardTitle>Student Registration</CardTitle>
                    <CardDescription>Please fill out the form to register a new student.</CardDescription>
                </CardHeader>
                <CardContent>
                    <div className="mb-8">
                        <Progress value={(currentStep + 1) / steps.length * 100} className="w-full" />
                    </div>
                    <form onSubmit={handleSubmit(onSubmit)} className="space-y-8">
                        {currentStep === 0 && (
                            <div className="space-y-4">
                                <h3 className="text-lg font-medium">Personal Information</h3>
                                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                                    <div className="space-y-2">
                                        <Label htmlFor="name">Full Name</Label>
                                        <Input
                                            id="name"
                                            aria-label="Full Name"
                                            {...register("name", { required: "Full name is required" })}
                                        />
                                        {errors.name && <p className="text-red-500 text-sm">{errors.name.message}</p>}
                                    </div>
                                    <div className="space-y-2">
                                        <Label htmlFor="email">Email Address</Label>
                                        <Input
                                            id="email"
                                            type="email"
                                            aria-label="Email Address"
                                            {...register("email", {
                                                required: "Email is required",
                                                pattern: {
                                                    value: /\S+@\S+\.\S+/,
                                                    message: "Invalid email address",
                                                }
                                            })}
                                        />
                                        {errors.email && <p className="text-red-500 text-sm">{errors.email.message}</p>}
                                    </div>
                                    <div className="space-y-2">
                                        <Label htmlFor="contact">Contact Number</Label>
                                        <Input
                                            id="contact"
                                            aria-label="Contact Number"
                                            {...register("contact")}
                                        />
                                    </div>
                                    <div className="space-y-2">
                                        <Label htmlFor="profilePicture">Profile Picture</Label>
                                        <Input
                                            id="profilePicture"
                                            type="file"
                                            aria-label="Profile Picture"
                                            {...register("profilePicture")}
                                            accept="image/*"
                                        />
                                    </div>
                                </div>
                            </div>
                        )}

                        {currentStep === 1 && (
                            <div className="space-y-4">
                                <h3 className="text-lg font-medium">Account Information</h3>
                                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                                    <div className="space-y-2">
                                        <Label htmlFor="username">Username</Label>
                                        <Input
                                            id="username"
                                            aria-label="Username"
                                            {...register("username", { required: "Username is required" })}
                                        />
                                        {errors.username && <p className="text-red-500 text-sm">{errors.username.message}</p>}
                                    </div>
                                    <div className="space-y-2">
                                        <Label htmlFor="password">Password</Label>
                                        <Input
                                            id="password"
                                            type="password"
                                            aria-label="Password"
                                            {...register("password", {
                                                required: "Password is required",
                                                minLength: {
                                                    value: 8,
                                                    message: "Password must be at least 8 characters",
                                                }
                                            })}
                                        />
                                        {errors.password && <p className="text-red-500 text-sm">{errors.password.message}</p>}
                                    </div>
                                </div>
                            </div>
                        )}

                        {currentStep === 2 && (
                            <div className="space-y-4">
                                <h3 className="text-lg font-medium">Academic Information</h3>
                                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                                    <div className="space-y-2">
                                        <Label htmlFor="regNum">Registration Number</Label>
                                        <Input
                                            id="regNum"
                                            type="number"
                                            aria-label="Registration Number"
                                            {...register("regNum", { required: "Registration number is required" })}
                                            className="[appearance:textfield] [&::-webkit-outer-spin-button]:appearance-none [&::-webkit-inner-spin-button]:appearance-none"
                                        />
                                        {errors.regNum && <p className="text-red-500 text-sm">{errors.regNum.message}</p>}
                                    </div>
                                    <div className="space-y-2">
                                        <Label htmlFor="department">Department</Label>
                                        <Controller
                                            name="department"
                                            control={control}
                                            rules={{ required: "Department is required" }}
                                            render={({ field }) => (
                                                <Select onValueChange={field.onChange} defaultValue={field.value}>
                                                    <SelectTrigger id="department" aria-label="department">
                                                        <SelectValue placeholder="Select department" />
                                                    </SelectTrigger>
                                                    <SelectContent>
                                                        <SelectItem value="cs">Computer Science</SelectItem>
                                                        <SelectItem value="ee">Electrical Engineering</SelectItem>
                                                        <SelectItem value="me">Mechanical Engineering</SelectItem>
                                                    </SelectContent>
                                                </Select>
                                            )}
                                        />
                                        {errors.specialization && <p className="text-red-500 text-sm">{errors.specialization.message}</p>}
                                    </div>
                                    <div className="space-y-2">
                                        <Label htmlFor="specialization">Specialization</Label>
                                        <Controller
                                            name="specialization"
                                            control={control}
                                            rules={{ required: "Specialization is required" }}
                                            render={({ field }) => (
                                                <Select onValueChange={field.onChange} defaultValue={field.value}>
                                                    <SelectTrigger id="specialization" aria-label="Specialization">
                                                        <SelectValue placeholder="Select specialization" />
                                                    </SelectTrigger>
                                                    <SelectContent>
                                                        <SelectItem value="cs">Computer Science</SelectItem>
                                                        <SelectItem value="ee">Electrical Engineering</SelectItem>
                                                        <SelectItem value="me">Mechanical Engineering</SelectItem>
                                                    </SelectContent>
                                                </Select>
                                            )}
                                        />
                                        {errors.specialization && <p className="text-red-500 text-sm">{errors.specialization.message}</p>}
                                    </div>
                                    <div className="space-y-2">
                                        <Label htmlFor="joinedAt">Date of Joining</Label>
                                        <Controller
                                            name="joinedAt"
                                            control={control}
                                            rules={{ required: "Join date is required" }}
                                            render={({ field }) => (
                                                <Popover>
                                                    <PopoverTrigger asChild>
                                                        <Button
                                                            variant={"outline"}
                                                            className={`w-full justify-start text-left font-normal ${!field.value && "text-muted-foreground"}`}
                                                        >
                                                            <CalendarIcon className="mr-2 h-4 w-4" />
                                                            {field.value ? format(new Date(field.value), "PPP") : <span>Pick a date</span>}
                                                        </Button>
                                                    </PopoverTrigger>
                                                    <PopoverContent className="w-auto p-0" align="start">
                                                        <Calendar
                                                            mode="single"
                                                            selected={field.value ? new Date(field.value) : undefined}
                                                            onSelect={(date) => field.onChange(date?.toISOString())}
                                                            initialFocus
                                                        />
                                                    </PopoverContent>
                                                </Popover>
                                            )}
                                        />
                                        {errors.joinedAt && <p className="text-red-500 text-sm">{errors.joinedAt.message}</p>}
                                    </div>
                                    <div className="space-y-2">
                                        <Label htmlFor="year">Current Year</Label>
                                        <Controller
                                            name="year"
                                            control={control}
                                            rules={{ required: "Year is required" }}
                                            render={({ field }) => (
                                                <Select onValueChange={field.onChange} defaultValue={field.value}>
                                                    <SelectTrigger id="year" aria-label="Current Year">
                                                        <SelectValue placeholder="Select year" />
                                                    </SelectTrigger>
                                                    <SelectContent>
                                                        <SelectItem value="1">1st Year</SelectItem>
                                                        <SelectItem value="2">2nd Year</SelectItem>
                                                        <SelectItem value="3">3rd Year</SelectItem>
                                                        <SelectItem value="4">4th Year</SelectItem>
                                                    </SelectContent>
                                                </Select>
                                            )}
                                        />
                                        {errors.year && <p className="text-red-500 text-sm">{errors.year.message}</p>}
                                    </div>
                                    <div className="space-y-2">
                                        <Label htmlFor="semester">Current Semester</Label>
                                        <Controller
                                            name="semester"
                                            control={control}
                                            rules={{ required: "Semester is required" }}
                                            render={({ field }) => (
                                                <Select onValueChange={field.onChange} defaultValue={field.value}>
                                                    <SelectTrigger id="semester" aria-label="Current Semester">
                                                        <SelectValue placeholder="Select semester" />
                                                    </SelectTrigger>
                                                    <SelectContent>
                                                        <SelectItem value="odd">Odd Semester</SelectItem>
                                                        <SelectItem value="even">Even Semester</SelectItem>
                                                    </SelectContent>
                                                </Select>
                                            )}
                                        />
                                        {errors.semester && <p className="text-red-500 text-sm">{errors.semester.message}</p>}
                                    </div>
                                </div>
                            </div>
                        )}

                        <div className="flex justify-between pt-6">
                            {currentStep > 0 && (
                                <Button type="button" onClick={prevStep} variant="outline">
                                    Previous
                                </Button>
                            )}
                            {currentStep < steps.length - 1 ? (
                                <Button type="button" onClick={nextStep}>
                                    Next
                                </Button>
                            ) : (
                                <Button type="submit" disabled={loading}>
                                    {loading ? 'Creating Student...' : 'Create Student'}
                                </Button>
                            )}
                        </div>
                    </form>
                </CardContent>
                <CardFooter>
                    {error && (
                        <Alert variant="destructive">
                            <AlertCircle className="h-4 w-4" />
                            <AlertTitle>Error</AlertTitle>
                            <AlertDescription>{error}</AlertDescription>
                        </Alert>
                    )}
                    {success && (
                        <Alert>
                            <CheckCircle className="h-4 w-4" />
                            <AlertTitle>Success</AlertTitle>
                            <AlertDescription>Student has been successfully created!</AlertDescription>
                        </Alert>
                    )}
                </CardFooter>
            </Card>
        </div>
    )
}