import {useEffect, useState} from "react";

interface usePageObserverProps {
  callback: (value?: any) => void
}
const usePageObserver = ({ callback }: usePageObserverProps) => {
  const [page, setPage] = useState(0);
  const [last, setLast] = useState<HTMLAnchorElement | null>();

  const onIntersect: IntersectionObserverCallback = (entries, observer) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting) {
        setPage(page + 1);
        callback(page + 1)
        observer.unobserve(entry.target);
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

  return { setLast }
}

export default usePageObserver;