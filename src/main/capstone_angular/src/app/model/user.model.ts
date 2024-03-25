export interface User {
  id: string | null;
  username: string;
  firstName: string;
  lastName: string;
  phoneNumber: string;
  email: string;
  role?: string; // Optional property
}
