package org.openstack.ui.client.compute.server;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.openstack.model.compute.Server;
import org.openstack.model.compute.nova.NovaAddressList.Network;
import org.openstack.model.compute.nova.NovaAddressList.Network.Ip;
import org.openstack.model.compute.nova.server.actions.ConfirmResizeAction;
import org.openstack.model.compute.nova.server.actions.Console;
import org.openstack.model.compute.nova.server.actions.CreateImageAction;
import org.openstack.model.compute.nova.server.actions.GetConsoleOutputAction;
import org.openstack.model.compute.nova.server.actions.GetVncConsoleAction;
import org.openstack.model.compute.nova.server.actions.InjectNetworkInfoAction;
import org.openstack.model.compute.nova.server.actions.LockAction;
import org.openstack.model.compute.nova.server.actions.Output;
import org.openstack.model.compute.nova.server.actions.PauseAction;
import org.openstack.model.compute.nova.server.actions.RebuildAction;
import org.openstack.model.compute.nova.server.actions.ResetNetworkAction;
import org.openstack.model.compute.nova.server.actions.ResumeAction;
import org.openstack.model.compute.nova.server.actions.RevertResize;
import org.openstack.model.compute.nova.server.actions.SuspendAction;
import org.openstack.model.compute.nova.server.actions.UnlockAction;
import org.openstack.model.compute.nova.server.actions.UnpauseAction;
import org.openstack.portal.client.Portal;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ServerDetails extends Composite {

	private static ServerDetailsUiBinder uiBinder = GWT
			.create(ServerDetailsUiBinder.class);

	interface ServerDetailsUiBinder extends UiBinder<Widget, ServerDetails> {
	
	}
	
	public interface Listener extends Reboot.Listener, RebuildServerWizard.Listener, Resize.Listener, ChangePassword.Listener, CreateImageWizard.Listener, CreateBackup.Listener  {
		void onResizeConfirmed(Server server);
		void onResizeReverted(Server server);
		void onServerPaused(Server server);
		void onServerUnpaused(Server server);
	}
	
	private Listener listener;
	
	private Server server;
	
	@UiField Label id;
	@UiField Label configDrive;
	@UiField Label created;
	@UiField Label updated;
	@UiField Label fault;
	@UiField Label image;
	@UiField Label flavor;
	@UiField Label hostId;
	@UiField Label keyName;
	@UiField Label progress;
	@UiField Label status;
	@UiField Label tenantId;
	@UiField Label userId;
	
	@UiField Label accessIPv4;
	@UiField Label accessIPv6;
	
	@UiField FlexTable metadata;
	@UiField VerticalPanel networks;

	public ServerDetails() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setListener(Listener listener) {
		this.listener = listener;
	}
	
	public void setServer(Server server) {
		this.server = server;
	}
	
	@UiHandler("reboot")
	void onRebootClick(ClickEvent evt) {
		Reboot widget = new Reboot();
		widget.setListener(listener);
		Portal.MODAL.setWidget(widget);
		Portal.MODAL.center();
	}
	
	@UiHandler("createImage")
	void onCreateImageClick(ClickEvent evt) {
		CreateImageWizard widget = new CreateImageWizard();
		widget.setServer(server);
		widget.setListener(listener);
		widget.edit(new CreateImageAction());
		Portal.MODAL.setWidget(widget);
		Portal.MODAL.center();
	}
	
	@UiHandler("rebuild")
	void onRebuildClick(ClickEvent evt) {
		RebuildServerWizard widget = new RebuildServerWizard();
		widget.setServer(server);
		widget.setListener(listener);
		widget.edit(new RebuildAction());
		Portal.MODAL.setWidget(widget);
		Portal.MODAL.center();
	}
	
	@UiHandler("resize")
	void onResizeClick(ClickEvent evt) {
		Resize widget = new Resize();
		widget.setServer(server);
		widget.setListener(listener);
		Portal.MODAL.setWidget(widget);
		Portal.MODAL.center();
	}
	
	@UiHandler("confirmResize")
	void onConfirmResizeClick(ClickEvent evt) {
		Portal.CLOUD.executeServerAction(server.getId(), new ConfirmResizeAction(), new AsyncCallback<Serializable>() {
			
			@Override
			public void onSuccess(Serializable result) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@UiHandler("revertResize")
	void onReverResizeClick(ClickEvent evt) {
		Portal.CLOUD.executeServerAction(server.getId(), new RevertResize(), new AsyncCallback<Serializable>() {
			
			@Override
			public void onSuccess(Serializable result) {
				
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@UiHandler("pause")
	void onPauseClick(ClickEvent evt) {
		Portal.CLOUD.executeServerAction(server.getId(), new PauseAction(), new AsyncCallback<Serializable>() {
			
			@Override
			public void onSuccess(Serializable result) {
				listener.onServerPaused(server);
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@UiHandler("unpause")
	void onUnpauseClick(ClickEvent evt) {
		Portal.CLOUD.executeServerAction(server.getId(), new UnpauseAction(), new AsyncCallback<Serializable>() {
			
			@Override
			public void onSuccess(Serializable result) {
				listener.onServerUnpaused(server);
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@UiHandler("lock")
	void onLockClick(ClickEvent evt) {
		Portal.CLOUD.executeServerAction(server.getId(), new LockAction(), new AsyncCallback<Serializable>() {
			
			@Override
			public void onSuccess(Serializable result) {
				
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@UiHandler("unlock")
	void onUnlockClick(ClickEvent evt) {
		Portal.CLOUD.executeServerAction(server.getId(), new UnlockAction(), new AsyncCallback<Serializable>() {
			
			@Override
			public void onSuccess(Serializable result) {
				
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@UiHandler("suspend")
	void onSuspendClick(ClickEvent evt) {
		Portal.CLOUD.executeServerAction(server.getId(), new SuspendAction(), new AsyncCallback<Serializable>() {
			
			@Override
			public void onSuccess(Serializable result) {
				
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@UiHandler("resume")
	void onResumeClick(ClickEvent evt) {
		Portal.CLOUD.executeServerAction(server.getId(), new ResumeAction(), new AsyncCallback<Serializable>() {
			
			@Override
			public void onSuccess(Serializable result) {
				
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@UiHandler("resetNetworking")
	void onResetNetworkingClick(ClickEvent evt) {
		Portal.CLOUD.executeServerAction(server.getId(), new ResetNetworkAction(), new AsyncCallback<Serializable>() {
			
			@Override
			public void onSuccess(Serializable result) {
				Window.alert(result.toString());
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@UiHandler("injectNetworkInfo")
	void onInjectNetworkInfoClick(ClickEvent evt) {
		Portal.CLOUD.executeServerAction(server.getId(), new InjectNetworkInfoAction(), new AsyncCallback<Serializable>() {
			
			@Override
			public void onSuccess(Serializable result) {
				Window.alert(result.toString());
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	@UiHandler("createBackup")
	void onCreateBackupClick(ClickEvent evt) {
		CreateBackup widget = new CreateBackup();
		widget.setServer(server);
		widget.setListener(listener);
		Portal.MODAL.setWidget(widget);
		Portal.MODAL.center();
	}
	
	@UiHandler("changePassword")
	void onChangePasswordClick(ClickEvent evt) {
		ChangePassword widget = new ChangePassword();
		widget.setServer(server);
		widget.setListener(listener);
		Portal.MODAL.setWidget(widget);
		Portal.MODAL.center();
	}
	
	@UiHandler("vnc")
	void onVncClick(ClickEvent evt) {
		final org.openstack.model.compute.ServerAction action = new GetVncConsoleAction();
		Portal.CLOUD.executeServerAction(server.getId(), action, new AsyncCallback<Serializable>() {

			@Override
			public void onFailure(Throwable caught) {

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
			}
		});
	}
	
	@UiHandler("console")
	void onConsoleClick(ClickEvent evt) {
		final org.openstack.model.compute.ServerAction action = new GetConsoleOutputAction();
		Portal.CLOUD.executeServerAction(server.getId(), action, new AsyncCallback<Serializable>() {

			@Override
			public void onFailure(Throwable caught) {
				
				
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
			}
		});
	}


	public void bind() {
		id.setText(server.getId());
		configDrive.setText(server.getConfigDrive());
		created.setText(server.getCreated().toString());
		updated.setText(server.getUpdated().toString());
		if(server.getFault() != null) {
			fault.setText(server.getFault().getMessage());
		}
		image.setText(server.getImage().getId());
		flavor.setText(server.getFlavor().getId());
		hostId.setText(server.getHostId());
		keyName.setText(server.getKeyName());
		progress.setText(server.getProgress());
		status.setText(server.getStatus());
		tenantId.setText(server.getTenantId());
		userId.setText(server.getUserId());
		accessIPv4.setText(server.getAccessIPv4());
		accessIPv6.setText(server.getAccessIPv6());
		
		int row = 0;
		for(Map.Entry<String, String> entry : server.getMetadata().entrySet()) {
			metadata.setText(row, 0, entry.getKey());
			metadata.setText(row, 1, entry.getValue());
			row++;
		}
		
		for(Map.Entry<String, List<Network.Ip>> n : server.getAddresses().entrySet()) {
			VerticalPanel network = new VerticalPanel();
			network.setWidth("100%");
			network.add(new HTML("<h4>" + n.getKey() + "<h4>"));
			FlexTable ips = new FlexTable();
			ips.setStyleName("table table-striped");
			row = 0;
			for(Ip ip : n.getValue()) {
				ips.setText(row, 0, ip.getVersion());
				ips.setText(row, 0, ip.getAddr());
				row++;
			}
			network.add(ips);
			
			networks.add(network);			
		}
		
	}

}
