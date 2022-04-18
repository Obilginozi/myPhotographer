import dayjs from 'dayjs';
import { IFile } from 'app/shared/model/file.model';
import { IClient } from 'app/shared/model/client.model';
import { FileWithPath } from 'react-dropzone';

export interface IAlbum {
  id?: string;
  token?: string | null;
  createDate?: string | null;
  files?: [{ id; name; photo; photoContentType }] | null;
  client?: IClient | null;
}

export const defaultValue: Readonly<IAlbum> = {};
