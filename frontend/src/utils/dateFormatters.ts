export const timeFormatter = new Intl.DateTimeFormat('ru-Ru', {
    hour: '2-digit',
    minute: '2-digit',
});

export function formatTimeSafe(dateString?: string): string {
  if (!dateString) return "";
  const date = new Date(dateString);
  if (isNaN(date.getTime())) return "";
  return timeFormatter.format(date);
}
