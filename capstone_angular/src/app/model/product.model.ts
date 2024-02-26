
export interface Product {
  id?: number;
  name: string;
  description?: string;
  price: number | null;
  quantity: number | null;
  category?: string;
}
