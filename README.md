# Enhancement of University Course Registration Process

## 1. Current Problems

**Description:**

During each semester's course registration process, students often face several challenges, including:

- Timetable clashes
- Unavailability of sections due to delayed access
- Confusion when selecting sections based on clusters and courses based on specializations
- Incorrect course selections that do not align with their intended registration
- Exceeding the designated credit limit
- Server timeouts due to heavy traffic

## 2. Proposed Solution

**Overview:**

- Default Clusters: Create default course-time slot templates for each cluster to streamline the process.
- Section Priority: Allow students to set a priority order for sections. If their preferred section is full, the system will automatically register them for the next available section in their priority list.
- In the worst case, when all selected sections are full, students will be shown a list of available sections that do not conflict with their already registered courses.
- Visibility Restrictions: Limit the visibility of courses based on specific restrictions or rules to reduce confusion.
- Informed Decisions: Provide students with information about a section’s faculty experience and course handouts to help them make informed choices.
- Credit Limit Enforcement: Prevent students from registering for courses that exceed their designated credit limit.
- Two-Stage Registration: Implement a two-stage registration process to reduce traffic intensity on registration days.
- Room Allocation: Assign rooms based on the course delivery component and room type. For example, labs will be allocated to practical components, lecture rooms to lectures, and only rooms with capacity greater than or equal to the section’s capacity will be shown.

**Benefits:**

- Improved User Experience
- Enhanced Operational Efficiency
- Scalability and Flexibility

## 3. Tech Stack

**Frontend:**

- React: To build an interactive user interface and ensure a smooth user experience.

**Backend:**

- Spring Boot: Provides a robust and scalable backend, facilitating RESTful API development.

**Database:**

- MongoDB: A NoSQL database chosen for its flexibility and scalability to handle large datasets.

**Infrastructure:**

- AWS: Cloud platform to host the application, ensuring high availability, security, and scalability.

**Containerization:**

- Docker: Ensures consistency across multiple environments and simplifies the deployment process.

## 4. Expected Outcomes

**Results:**

- Increased Efficiency: Improved registration efficiency and user satisfaction.
- Reduced Errors: Fewer errors in course selections and timetable clashes.
- Enhanced Performance: Improved system performance and scalability.

## 5. User Interface and Roles

### Student UI:

- Registration Dashboard: Allows students to view available courses, set priorities, and register for courses.
- Course Information Page: Provides details about course content, faculty experience, and course handouts.
- Conflict Resolution Interface: Displays alternative sections and manages conflicts.

### Admin UI:

- Course Management Panel: Enables admins to manage course offerings, set priorities, and update section availability.
- Room Allocation Interface: Manages room assignments based on course requirements.
- Traffic Monitoring Dashboard: Monitors system performance and manages traffic load during registration periods (using Google Analytics and AWS ELB).

### Faculty UI:

- Student Interface Access: Faculty can view which students from their counseling group are ready to submit their choices (needs clarification).
- Course Management: Allows faculty to update course handouts for courses where they are the Course Coordinator.

### Admin - Super Admin Role Responsibilities, Flow

- **Super Admin**: Can add/manage admins and has access to all data.
- **Admin**: 
  - Can manage everything within the system, including departments, specializations, batches, student accounts, faculty accounts, courses, and registrations, regardless of who created them.
  - Admins have full access to create, edit, and delete any data within the system, except for managing other admins (which is limited to Super Admins).

---

### Admin Responsibilities Before Initializing Registrations

1. **Create/Initialize Departments, Specializations, Batches**  
   Admins can create, edit, and manage departments, specializations, and batches for the academic year (Ex: Y22, Y23, Y24), with no restrictions based on who created the data.

2. **Create Student Accounts**  
   Admins can create student accounts and map them to specific batches, departments, and specializations.  
   - Admins can also edit or delete any student account as needed.  
   - **Bulk Upload**: Admins can bulk upload student data via CSV.

3. **Create Faculty Accounts**  
   Admins can create and manage faculty accounts, assigning them to departments.  
   - Admins have full access to edit or delete any faculty account in the system, regardless of who initially created it.

4. **Create Courses**  
   Admins can create and manage courses, link them to departments, specializations, and prerequisites, and update course handouts (PDFs).

5. **Assign Courses to Faculty**  
   Admins can assign courses to faculty members for any department or specialization.

6. **Manage Rooms for Classes**  
   Admins can create, edit, and manage rooms for all courses, ensuring proper room allocation.

---

### Flow of Creation or Initialization of Registration

1. **Registration Period Initialization**  
   A registration must be initialized 2-3 days before the actual date. Admins are responsible for setting up registration configurations for the departments, batches, and students they manage.

2. **Creating Clusters with Schedules**  
   Admins can create clusters using schedule templates and define section limits for students. Admins can freely manage and edit these clusters as needed.

3. **Assigning Components for Registration**  
   Admins select and manage components (sections, rooms, faculty, timeslots) for the registration setup.

---

### Additional Considerations

- **Audit Logs**: Include an audit log to track changes and updates made by each admin for accountability and transparency.
- **Manual Adjustments**: Admins should have the ability to manually adjust room or faculty assignments in case of conflicts.
- **Notifications**: Add notifications to alert admins about upcoming registration periods or missing data.
- **Dashboard Analytics**: A dashboard showing key metrics like student registrations, course loads, and faculty assignments would be helpful.


## 6. Program Flow

### Student Registration:

1. Students log in and access the registration dashboard.
2. They set priorities for their preferred sections and review course information well before the registration date.
3. On registration day, students can only access the portal after paying their semester fee, which is marked as paid in ERP (assuming two portals share the same database).
4. The system processes their registration while avoiding conflicts.

### Admin Management:

1. Admins update course details and manage room allocations.
2. Admins can create users (Faculties, Students).
3. Admins can also assist with course registration if students face any issues.
4. They monitor traffic and ensure smooth registration operations.

### Faculty Interaction:

1. Faculty update course-related information and access registration data as needed.
2. Faculty can also provide students access to submit their course choices.

## 7. Conclusion

Addressing the current registration challenges is crucial for improving the student experience. The proposed solution, backed by a modern tech stack, offers a comprehensive and effective approach to resolving these issues.
