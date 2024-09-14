import useFetchUsers from "./hooks/useFetchUsers";
import { Button, Card, CardBody, Typography } from "@material-tailwind/react";

const App = () => {
  const { users, loading, error } = useFetchUsers();

  if (loading)
    return (
      <Typography variant="h2" color="blue" className="font-bold">
        Loading...
      </Typography>
    );
  if (error)
    return (
      <Typography variant="h2" color="red" className="font-bold">
        Error: {error}
      </Typography>
    );

  return (
    <Card className="w-96 mx-auto mt-10">
      <CardBody>
        <Typography variant="h1" color="blue-gray" className="mb-4">
          Hello world!
        </Typography>
        <Button color="blue" ripple={true} className="mb-4">
          Material Tailwind Button
        </Button>
        <Typography variant="h2" color="blue-gray" className="mb-2">
          Users List
        </Typography>
        {users.length === 0 ? (
          <Typography color="gray">No users found</Typography>
        ) : (
          <ul className="list-disc pl-5">
            {users.map((user, index) => (
              <li key={user.id || index} className="text-indigo-600">
                <Typography>{user.username}</Typography>
              </li>
            ))}
          </ul>
        )}
      </CardBody>
    </Card>
  );
};

export default App;
