import {useEffect, useState} from "react";

const usePageObserver = () => {
  const [page, setPage] = useState(0);
  const [last, setLast] = useState<HTMLAnchorElement | null>();

  const onIntersect: IntersectionObserverCallback = (playlists, observer) => {
    playlists.forEach((playlist) => {
      if (playlist.isIntersecting) {
        setPage(page + 1);
        observer.unobserve(playlist.target);
      }
    });
  };

  useEffect(() => {
    let observer: IntersectionObserver;
    if (last) {
      observer = new IntersectionObserver(onIntersect, { threshold: 0.5 });
      observer.observe(last);
    }
    return () => observer && observer.disconnect();
  }, [last]);

  return { page, setLast }
}

export default usePageObserver;