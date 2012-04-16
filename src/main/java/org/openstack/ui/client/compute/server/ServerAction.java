package org.openstack.ui.client.compute.server;

import java.io.Serializable;

import org.openstack.model.compute.Server;
import org.openstack.model.compute.nova.server.actions.Console;
import org.openstack.model.compute.nova.server.actions.GetConsoleOutputAction;
import org.openstack.model.compute.nova.server.actions.GetVncConsoleAction;
import org.openstack.model.compute.nova.server.actions.Output;
import org.openstack.portal.client.Portal;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;

public enum ServerAction {
	
	RESTORE {

		@Override
		public void execute(Server server, final ServerActionPicker.Listener listener) {
			final org.openstack.model.compute.ServerAction action = new GetVncConsoleAction();
			Portal.CLOUD.executeServerAction(server.getId(), action, new AsyncCallback<Serializable>() {

				@Override
				public void onFailure(Throwable caught) {
					
					listener.onServerActionFailure(action, caught);
					
				}

				@Override
				public void onSuccess(Serializable result) {
					Console console = (Console) result;
					Frame frame = new Frame(console.getUrl());
					frame.setHeight("430px");
					frame.setWidth("720px");
					Portal.MODAL.setWidget(frame);
					Portal.MODAL.center();
					frame.getElement().focus();
					listener.onServerActionSuccess(action, result);
				}
			});
		}

	},
	FORCE_DELETE {

		@Override
		public void execute(Server server, final ServerActionPicker.Listener listener) {
			final org.openstack.model.compute.ServerAction action = new GetConsoleOutputAction();
			Portal.CLOUD.executeServerAction(server.getId(), action, new AsyncCallback<Serializable>() {

				@Override
				public void onFailure(Throwable caught) {
					listener.onServerActionFailure(action, caught);
					
				}

				@Override
				public void onSuccess(Serializable result) {
					Output output = (Output) result;
					ScrollPanel scroll = new ScrollPanel();
					scroll.setWidth("980px");
					scroll.setHeight("400px");
					SafeHtmlBuilder builder = new SafeHtmlBuilder();
					builder.appendHtmlConstant("<code>").appendEscapedLines(output.getOutput()).appendHtmlConstant("<code>");
					HTML html = new HTML(builder.toSafeHtml());
					scroll.add(html);
					Portal.MODAL.setWidget(scroll);
					Portal.MODAL.center();
					scroll.scrollToBottom();
					listener.onServerActionSuccess(action, result);
					
				}
			});
		}

	};
	/*
	CHANGE_PASSWORD {

		@Override
		public void execute(Server server, final ServerActionPicker.Listener listener) {
			final org.openstack.model.compute.ServerAction action = new RestoreAction();
			Portal.CLOUD.executeServerAction(server.getId(), action , new AsyncCallback<Serializable>() {

				@Override
				public void onFailure(Throwable caught) {
					listener.onServerActionFailure(action, caught);
					
				}

				@Override
				public void onSuccess(Serializable result) {
					listener.onServerActionSuccess(action, result);
					
				}
			});
		}

	},
	REBUILD {

		@Override
		public void execute(Server server, final ServerActionPicker.Listener listener) {
			final org.openstack.model.compute.ServerAction action = new RestoreAction();
			Portal.CLOUD.executeServerAction(server.getId(), action , new AsyncCallback<Serializable>() {

				@Override
				public void onFailure(Throwable caught) {
					listener.onServerActionFailure(action, caught);
					
				}

				@Override
				public void onSuccess(Serializable result) {
					listener.onServerActionSuccess(action, result);
					
				}
			});
		}

	},
	RESIZE {

		@Override
		public void execute(Server server, final ServerActionPicker.Listener listener) {
			final org.openstack.model.compute.ServerAction action = new RestoreAction();
			Portal.CLOUD.executeServerAction(server.getId(), action , new AsyncCallback<Serializable>() {

				@Override
				public void onFailure(Throwable caught) {
					listener.onServerActionFailure(action, caught);
					
				}

				@Override
				public void onSuccess(Serializable result) {
					listener.onServerActionSuccess(action, result);
					
				}
			});
		}

	},
	CONFIRM_RESIZE {

		@Override
		public void execute(Server server, final ServerActionPicker.Listener listener) {
			final org.openstack.model.compute.ServerAction action = new RestoreAction();
			Portal.CLOUD.executeServerAction(server.getId(), action , new AsyncCallback<Serializable>() {

				@Override
				public void onFailure(Throwable caught) {
					listener.onServerActionFailure(action, caught);
					
				}

				@Override
				public void onSuccess(Serializable result) {
					listener.onServerActionSuccess(action, result);
					
				}
			});
		}

	},
	REVERT_RESIZE {

		@Override
		public void execute(Server server, final ServerActionPicker.Listener listener) {
			final org.openstack.model.compute.ServerAction action = new RestoreAction();
			Portal.CLOUD.executeServerAction(server.getId(), action , new AsyncCallback<Serializable>() {

				@Override
				public void onFailure(Throwable caught) {
					listener.onServerActionFailure(action, caught);
					
				}

				@Override
				public void onSuccess(Serializable result) {
					listener.onServerActionSuccess(action, result);
					
				}
			});
		}

	},
	CREATE_IMAGE {

		@Override
		public void execute(Server server, final ServerActionPicker.Listener listener) {
			final org.openstack.model.compute.ServerAction action = new RestoreAction();
			Portal.CLOUD.executeServerAction(server.getId(), action , new AsyncCallback<Serializable>() {

				@Override
				public void onFailure(Throwable caught) {
					listener.onServerActionFailure(action, caught);
					
				}

				@Override
				public void onSuccess(Serializable result) {
					listener.onServerActionSuccess(action, result);
					
				}
			});
		}

	},
	PAUSE {

		@Override
		public void execute(Server server, final ServerActionPicker.Listener listener) {
			final org.openstack.model.compute.ServerAction action = new RestoreAction();
			Portal.CLOUD.executeServerAction(server.getId(), action , new AsyncCallback<Serializable>() {

				@Override
				public void onFailure(Throwable caught) {
					listener.onServerActionFailure(action, caught);
					
				}

				@Override
				public void onSuccess(Serializable result) {
					listener.onServerActionSuccess(action, result);
					
				}
			});
		}

	},
	UNPAUSE {

		@Override
		public void execute(Server server, final ServerActionPicker.Listener listener) {
			final org.openstack.model.compute.ServerAction action = new RestoreAction();
			Portal.CLOUD.executeServerAction(server.getId(), action , new AsyncCallback<Serializable>() {

				@Override
				public void onFailure(Throwable caught) {
					listener.onServerActionFailure(action, caught);
					
				}

				@Override
				public void onSuccess(Serializable result) {
					listener.onServerActionSuccess(action, result);
					
				}
			});
		}

	},
	SUSPEND {

		@Override
		public void execute(Server server, final ServerActionPicker.Listener listener) {
			final org.openstack.model.compute.ServerAction action = new RestoreAction();
			Portal.CLOUD.executeServerAction(server.getId(), action , new AsyncCallback<Serializable>() {

				@Override
				public void onFailure(Throwable caught) {
					listener.onServerActionFailure(action, caught);
					
				}

				@Override
				public void onSuccess(Serializable result) {
					listener.onServerActionSuccess(action, result);
					
				}
			});
		}

	},
	RESUME {

		@Override
		public void execute(Server server, final ServerActionPicker.Listener listener) {
			final org.openstack.model.compute.ServerAction action = new RestoreAction();
			Portal.CLOUD.executeServerAction(server.getId(), action , new AsyncCallback<Serializable>() {

				@Override
				public void onFailure(Throwable caught) {
					listener.onServerActionFailure(action, caught);
					
				}

				@Override
				public void onSuccess(Serializable result) {
					listener.onServerActionSuccess(action, result);
					
				}
			});
		}

	},
	MIGRATE {

		@Override
		public void execute(Server server, final ServerActionPicker.Listener listener) {
			final org.openstack.model.compute.ServerAction action = new RestoreAction();
			Portal.CLOUD.executeServerAction(server.getId(), action , new AsyncCallback<Serializable>() {

				@Override
				public void onFailure(Throwable caught) {
					listener.onServerActionFailure(action, caught);
					
				}

				@Override
				public void onSuccess(Serializable result) {
					listener.onServerActionSuccess(action, result);
					
				}
			});
		}

	},
	RESET_NETWORK {

		@Override
		public void execute(Server server, final ServerActionPicker.Listener listener) {
			final org.openstack.model.compute.ServerAction action = new RestoreAction();
			Portal.CLOUD.executeServerAction(server.getId(), action , new AsyncCallback<Serializable>() {

				@Override
				public void onFailure(Throwable caught) {
					listener.onServerActionFailure(action, caught);
					
				}

				@Override
				public void onSuccess(Serializable result) {
					listener.onServerActionSuccess(action, result);
					
				}
			});
		}

	},
	INJECT_NETWORK_INFO {

		@Override
		public void execute(Server server, final ServerActionPicker.Listener listener) {
			final org.openstack.model.compute.ServerAction action = new RestoreAction();
			Portal.CLOUD.executeServerAction(server.getId(), action , new AsyncCallback<Serializable>() {

				@Override
				public void onFailure(Throwable caught) {
					listener.onServerActionFailure(action, caught);
					
				}

				@Override
				public void onSuccess(Serializable result) {
					listener.onServerActionSuccess(action, result);
					
				}
			});
		}

	},
	LOCK {

		@Override
		public void execute(Server server, final ServerActionPicker.Listener listener) {
			final org.openstack.model.compute.ServerAction action = new RestoreAction();
			Portal.CLOUD.executeServerAction(server.getId(), action , new AsyncCallback<Serializable>() {

				@Override
				public void onFailure(Throwable caught) {
					listener.onServerActionFailure(action, caught);
					
				}

				@Override
				public void onSuccess(Serializable result) {
					listener.onServerActionSuccess(action, result);
					
				}
			});
		}

	},
	UNLOCK {

		@Override
		public void execute(Server server, final ServerActionPicker.Listener listener) {
			final org.openstack.model.compute.ServerAction action = new RestoreAction();
			Portal.CLOUD.executeServerAction(server.getId(), action , new AsyncCallback<Serializable>() {

				@Override
				public void onFailure(Throwable caught) {
					listener.onServerActionFailure(action, caught);
					
				}

				@Override
				public void onSuccess(Serializable result) {
					listener.onServerActionSuccess(action, result);
					
				}
			});
		}

	},
	GET_VNC_CONSOLE {

		@Override
		public void execute(Server server, final ServerActionPicker.Listener listener) {
			final org.openstack.model.compute.ServerAction action = new RestoreAction();
			Portal.CLOUD.executeServerAction(server.getId(), action , new AsyncCallback<Serializable>() {

				@Override
				public void onFailure(Throwable caught) {
					listener.onServerActionFailure(action, caught);
					
				}

				@Override
				public void onSuccess(Serializable result) {
					listener.onServerActionSuccess(action, result);
					
				}
			});
		}

	},
	GET_CONSOLE_OUTPUT {

		@Override
		public void execute(Server server, final ServerActionPicker.Listener listener) {
			final org.openstack.model.compute.ServerAction action = new RestoreAction();
			Portal.CLOUD.executeServerAction(server.getId(), action , new AsyncCallback<Serializable>() {

				@Override
				public void onFailure(Throwable caught) {
					listener.onServerActionFailure(action, caught);
					
				}

				@Override
				public void onSuccess(Serializable result) {
					listener.onServerActionSuccess(action, result);
					
				}
			});
		}

	};
	*/
	public abstract void execute(Server server, ServerActionPicker.Listener listener);

}