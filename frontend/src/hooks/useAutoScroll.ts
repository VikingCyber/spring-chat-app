import { useRef, useState } from "react";

export function useAutoScroll<T extends HTMLElement>() {
  const containerRef = useRef<T>(null);
  const endRef = useRef<HTMLDivElement>(null);
  const [isAtBottom, setIsAtBottom] = useState(true);

  const scrollToBottom = () => {
    endRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  const handleScroll = () => {
    const container = containerRef.current;
    if (!container) return;

    const atBottom = container.scrollHeight - container.scrollTop - container.clientHeight < 50;

    if (atBottom !== isAtBottom) {
      setIsAtBottom(atBottom);
    }
  };

  return {
    containerRef,
    endRef,
    isAtBottom,
    scrollToBottom,
    handleScroll,
  };
}
