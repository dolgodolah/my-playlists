import axios from "axios";

const Home = () => {

  const getApi = () => {
      axios.get("/api/hello")
          .then(res => {
              console.log(res);
          })
          .catch(res => console.log(res))
  }

  getApi();

  return (
    <>
      <main>
        <span>Home</span>
      </main>
    </>
  );
};

export default Home;
