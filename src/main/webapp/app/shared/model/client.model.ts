import { IAlbum } from 'app/shared/model/album.model';
import { ICompany } from 'app/shared/model/company.model';

export interface IClient {
  id?: string;
  clientName?: string | null;
  clientSurname?: string | null;
  clientMail?: string | null;
  clientPhone?: string | null;
  clientAddress?: string | null;
  albums?: IAlbum[] | null;
  company?: ICompany | null;
}

export const defaultValue: Readonly<IClient> = {};
