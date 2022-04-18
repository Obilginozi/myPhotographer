import React, { useEffect, useState } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { getEntity, reset } from './album.reducer';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import 'react-dropzone-uploader/dist/styles.css';
import axios from 'axios';
import { useDropzone } from 'react-dropzone';
import styled from 'styled-components';

const thumbsContainer = {
  display: 'flex',
  flexDirection: 'row',
  flexWrap: 'wrap',
  marginTop: 16,
};

const thumb = {
  display: 'inline-flex',
  borderRadius: 2,
  border: '1px solid #eaeaea',
  marginBottom: 8,
  marginRight: 8,
  width: 100,
  height: 100,
  padding: 4,
  boxSizing: 'border-box',
};

const thumbInner = {
  display: 'flex',
  minWidth: 0,
  overflow: 'hidden',
};

const img = {
  display: 'block',
  width: 'auto',
  height: '100%',
};

const getColor = props => {
  if (props.isDragAccept) {
    return '#00e676';
  }
  if (props.isDragReject) {
    return '#ff1744';
  }
  if (props.isDragActive) {
    return '#2196f3';
  }
  return '#eeeeee';
};

const Container = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  border-width: 2px;
  border-radius: 2px;
  border-color: ${props => getColor(props)};
  border-style: dashed;
  background-color: #fafafa;
  color: #bdbdbd;
  outline: none;
  transition: border 0.24s ease-in-out;
`;

export const AlbumUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const clients = useAppSelector(state => state.client.entities);
  const albumEntity = useAppSelector(state => state.album.entity);
  const loading = useAppSelector(state => state.album.loading);
  const updating = useAppSelector(state => state.album.updating);
  const updateSuccess = useAppSelector(state => state.album.updateSuccess);
  const apiUrl = 'api/albums';
  const albumMultipartFormParam = 'album';
  const filesMultipartFormParam = 'files';
  const formData: FormData = new FormData();

  const [files, setFiles] = useState([]);
  const { getRootProps, getInputProps } = useDropzone({
    accept: 'image/*',
    onDrop(acceptedFiles) {
      setFiles(
        acceptedFiles.map(file =>
          Object.assign(file, {
            preview: URL.createObjectURL(file),
          })
        )
      );
    },
  });

  const thumbs = files.map(file => (
    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore
    <div style={thumb} key={file.name}>
      <div style={thumbInner}>
        <img src={file.preview} style={img} alt={file.name} />
      </div>
    </div>
  ));

  useEffect(
    () => () => {
      // Make sure to revoke the data uris to avoid memory leaks
      // eslint-disable-next-line @typescript-eslint/ban-ts-comment
      // @ts-ignore
      files.forEach(file => URL.revokeObjectURL(file.preview));
    },
    [files]
  );

  const test = values => {
    const entity = {
      id: values.id,
      token: values.token,
      createDate: values.createDate,
      client: clients.find(it => it.id.toString() === values.client.toString()),
    };

    const albumAsJsonBlob: Blob = new Blob([JSON.stringify(entity)], { type: 'application/json' });
    const config = {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    };
    formData.append(albumMultipartFormParam, albumAsJsonBlob);
    for (let i = 0; i < files.length; i++) {
      formData.append(filesMultipartFormParam, files[i]);
    }
    axios.post<any>(apiUrl, formData, config);
  };

  const handleClose = () => {
    props.history.push('/album');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getClients({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const defaultValues = () =>
    isNew
      ? {
          ...albumEntity,
          files,
        }
      : {
          ...albumEntity,
          client: albumEntity?.client?.id,
          files,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="fotografcimApp.album.home.createOrEditLabel" data-cy="AlbumCreateUpdateHeading">
            <Translate contentKey="fotografcimApp.album.home.createOrEditLabel">Create or edit a Album</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={test} mode={'onSubmit'}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="album-id"
                  label={translate('fotografcimApp.album.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <section className="container">
                <Container {...getRootProps({ className: 'dropzone' })}>
                  <input {...getInputProps()} />
                  <span className="d-none d-md-inline">{translate('fotografcimApp.album.fileDialog')}</span>
                </Container>
                <aside
                  // eslint-disable-next-line @typescript-eslint/ban-ts-comment
                  // @ts-ignore
                  style={thumbsContainer}
                >
                  {thumbs}
                </aside>
              </section>
              <ValidatedField
                id="album-client"
                name="client"
                data-cy="client"
                label={translate('fotografcimApp.album.client')}
                type="select"
              >
                <option value="" key="0" />
                {clients
                  ? clients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.clientName + ' ' + otherEntity.clientSurname}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/album" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AlbumUpdate;
