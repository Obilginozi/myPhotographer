import { IUser } from 'app/shared/model/user.model';
import { IClient } from 'app/shared/model/client.model';

export interface ICompany {
  id?: string;
  companyName?: string | null;
  companyPhone?: string | null;
  companyMail?: string | null;
  companyAddress?: string | null;
  user?: IUser | null;
  clients?: IClient[] | null;
}

export const defaultValue: Readonly<ICompany> = {};
