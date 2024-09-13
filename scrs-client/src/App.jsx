import useFetchUsers from "./hooks/useFetchUsers"; // Adjust the path as needed

const App = () => {
  // Use the custom hook to fetch users
  const { users, loading, error } = useFetchUsers();

  // Render loading state
  if (loading) return <h2>Loading...</h2>;

  // Render error state
  if (error) return <h2>Error: {error}</h2>;

  // Render the user list
  return (
    <div>
      <h1 className="text-3xl font-bold underline">Hello world!</h1>
      <h1>Users List</h1>
      {users.length === 0 ? (
        <p>No users found</p>
      ) : (
        <ul>
          {users.map((user, index) => (
            <li key={user.id || index}>{user.username}</li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default App;
