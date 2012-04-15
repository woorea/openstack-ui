package org.cloudsherpa.ui.server;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.cloudsherpa.ui.client.UIService;
import org.openstack.model.compute.Flavor;
import org.openstack.model.compute.FlavorList;
import org.openstack.model.compute.FloatingIp;
import org.openstack.model.compute.KeyPair;
import org.openstack.model.compute.SecurityGroup;
import org.openstack.model.compute.SecurityGroupForCreate;
import org.openstack.model.compute.SecurityGroupRule;
import org.openstack.model.compute.SecurityGroupRuleForCreate;
import org.openstack.model.compute.Server;
import org.openstack.model.compute.ServerAction;
import org.openstack.model.compute.ServerForCreate;
import org.openstack.model.compute.ServerList;
import org.openstack.model.compute.Snapshot;
import org.openstack.model.compute.SnapshotForCreate;
import org.openstack.model.compute.Volume;
import org.openstack.model.compute.nova.volume.VolumeForCreate;
import org.openstack.model.identity.Endpoint;
import org.openstack.model.identity.Role;
import org.openstack.model.identity.Service;
import org.openstack.model.identity.Tenant;
import org.openstack.model.identity.TenantList;
import org.openstack.model.identity.User;
import org.openstack.model.identity.UserForCreate;
import org.openstack.model.images.Image;
import org.openstack.model.images.ImageList;

import com.google.gwt.core.client.GWT;

public class UIServiceImpl extends OpenStackRemoteServiceServlet implements UIService {

	@Override
	public ServerList listServers(int start, int max) {
		GWT.log("test!!!!");
		return getComputeClient().listServers();
	}

	@Override
	public Server create(ServerForCreate serverForCreate) {
		return getComputeClient().createServer(serverForCreate);
	}

	@Override
	public void deleteServer(String id) {
		getComputeClient().deleteServer(id);
		
	}

	@Override
	public void deleteServers(String[] ids) {
		for(String id : ids) {
			deleteServer(id);
		}
	}

	@Override
	public Serializable executeServerAction(Collection<String> id, ServerAction action) {
		//getComputeClient();
		return "";
	}

	@Override
	public FlavorList listFlavors(int start, int max) {
		return getComputeClient().listFlavors();
	}

	@Override
	public Flavor create(Flavor flavor) {
		//return getComputeClient();
		return null;
	}

	@Override
	public void deleteFlavor(String id) {
		getComputeClient().deleteFlavor(id);
		
	}

	@Override
	public void deleteFlavors(String[] ids) {
		for(String id : ids) {
			deleteFlavor(id);
		}
		
	}

	@Override
	public ImageList listImages(int starts, int max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image create(Image image) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteImage(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteImages(String[] id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<FloatingIp> listFloatingIps() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FloatingIp createFloatingIp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteFloatingIp(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFloatingIps(Integer[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FloatingIp associateFloatingIp(String ip, String serverId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FloatingIp disassociateFloatingIp(String ip) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Volume> listVolumes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Volume create(VolumeForCreate volumeForCreate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteVolume(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteVolumes(Integer[] id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Volume attachVolume(Integer id, String serverId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Volume detachVolume(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Snapshot> listSnapshots() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Snapshot create(SnapshotForCreate snapshotForCreate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteSnapshot(Integer ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteSnapshots(Integer[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<KeyPair> listKeyPairs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KeyPair createKeyPair(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteKeyPair(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteKeyPairs(String[] names) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SecurityGroup> listSecurityGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SecurityGroup create(SecurityGroupForCreate securityGroup) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SecurityGroup showSecurityGroup(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteSecurityGroup(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteSecurityGroups(Integer[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SecurityGroupRule create(SecurityGroupRuleForCreate rule) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteSecurityGroupRule(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteSecurityGroupRules(Integer[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public TenantList listTenants(int start, int max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tenant create(Tenant tenant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteTenant(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTenants(String[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> listUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User create(UserForCreate userForCreate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUsers(String[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Role> listRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role create(Role role) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRole(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteRoles(String[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Service> listServices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Service create(Service service) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteService(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteServices(String[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Endpoint> listEndpoints() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Endpoint create(Endpoint endpoint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteEndpoint(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteEndpoints(String[] ids) {
		// TODO Auto-generated method stub
		
	}

}
