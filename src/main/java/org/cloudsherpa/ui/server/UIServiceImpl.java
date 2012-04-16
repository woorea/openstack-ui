package org.cloudsherpa.ui.server;

import java.io.Serializable;
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

public class UIServiceImpl extends OpenStackRemoteServiceServlet implements UIService {

	@Override
	public ServerList listServers(int start, int max) {
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
	public Serializable executeServerAction(String id, ServerAction action) {
		return getComputeClient().executeServerAction(id, action);
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
		return getImagesClient().listImages();
	}

	@Override
	public Image create(Image image) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteImage(String id) {
		//getComputeClient().deleteImage(id);
		
	}

	@Override
	public void deleteImages(String[] ids) {
		for(String id : ids) {
			deleteImage(id);
		}
		
	}

	@Override
	public List<FloatingIp> listFloatingIps() {
		return getComputeClient().listFloatingIps();
	}

	@Override
	public FloatingIp createFloatingIp() {
		return getComputeClient().createFloatingIp(null);
	}

	@Override
	public void deleteFloatingIp(Integer id) {
		getComputeClient().deleteFloatingIp(id);
		
	}

	@Override
	public void deleteFloatingIps(Integer[] ids) {
		for(Integer id : ids) {
			deleteFloatingIp(id);
		}
		
	}

	@Override
	public FloatingIp associateFloatingIp(String ip, String serverId) {
		//getComputeClient().action();
		return null;
	}

	@Override
	public FloatingIp disassociateFloatingIp(String ip) {
		//getComputeClient().action();
		return null;
	}

	@Override
	public List<Volume> listVolumes() {
		return getComputeClient().listVolumes();
	}

	@Override
	public Volume create(VolumeForCreate volumeForCreate) {
		return getComputeClient().createVolume(volumeForCreate);
	}

	@Override
	public void deleteVolume(Integer id) {
		getComputeClient().deleteVolume(id);
		
	}

	@Override
	public void deleteVolumes(Integer[] ids) {
		for(Integer id : ids) {
			deleteVolume(id);
		}
		
	}

	@Override
	public Volume attachVolume(Integer id, String serverId) {
		// serveraction
		return null;
	}

	@Override
	public Volume detachVolume(Integer id) {
		// serveraction
		return null;
	}

	@Override
	public List<Snapshot> listSnapshots() {
		return getComputeClient().listSnapshots();
	}

	@Override
	public Snapshot create(SnapshotForCreate snapshotForCreate) {
		return getComputeClient().createSnapshot(snapshotForCreate);
	}

	@Override
	public void deleteSnapshot(Integer id) {
		getComputeClient().deleteSnapshot(id);
		
	}

	@Override
	public void deleteSnapshots(Integer[] ids) {
		for(Integer id : ids) {
			deleteSnapshot(id);
		}
		
	}

	@Override
	public List<KeyPair> listKeyPairs() {
		return getComputeClient().listKeyPairs();
	}

	@Override
	public KeyPair createKeyPair(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteKeyPair(String name) {
		getComputeClient().deleteKeyPair(name);
		
	}

	@Override
	public void deleteKeyPairs(String[] names) {
		for(String name : names) {
			deleteKeyPair(name);
		}
		
	}

	@Override
	public List<SecurityGroup> listSecurityGroups() {
		return getComputeClient().listSecurityGroups();
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
		getComputeClient().deleteSecurityGroup(id);
		
	}

	@Override
	public void deleteSecurityGroups(Integer[] ids) {
		for(Integer id : ids) {
			deleteSecurityGroup(id);
		}
		
	}

	@Override
	public SecurityGroupRule create(SecurityGroupRuleForCreate rule) {
		return getComputeClient().createSecurityGroupRule(rule);
	}

	@Override
	public void deleteSecurityGroupRule(Integer id) {
		//getComputeClient().deleteSecurityGroupRule(id);
		
	}

	@Override
	public void deleteSecurityGroupRules(Integer[] ids) {
		for(Integer id : ids) {
			deleteSecurityGroupRule(id);
		}
		
	}

	@Override
	public TenantList listTenants(int start, int max) {
		return getIdentityClient().listTenants(start, max);
	}

	@Override
	public Tenant create(Tenant tenant) {
		return getIdentityClient().createTenant(tenant);
	}

	@Override
	public void deleteTenant(String id) {
		getIdentityClient().deleteTenant(id);
		
	}

	@Override
	public void deleteTenants(String[] ids) {
		for(String id : ids) {
			deleteTenant(id);
		}
		
	}

	@Override
	public List<User> listUsers() {
		return getIdentityClient().listUsers();
	}

	@Override
	public User create(UserForCreate userForCreate) {
		return getIdentityClient().createUser(userForCreate);
	}

	@Override
	public void deleteUser(String id) {
		getIdentityClient().deleteUser(id);
		
	}

	@Override
	public void deleteUsers(String[] ids) {
		for(String id : ids) {
			deleteUser(id);
		}
		
	}

	@Override
	public List<Role> listRoles() {
		return getIdentityClient().listRoles();
	}

	@Override
	public Role create(Role role) {
		return getIdentityClient().createRole(role);
	}

	@Override
	public void deleteRole(String id) {
		getIdentityClient().deleteRole(id);
		
	}

	@Override
	public void deleteRoles(String[] ids) {
		for(String id : ids) {
			deleteRole(id);
		}
		
	}

	@Override
	public List<Service> listServices() {
		return getIdentityClient().listServices();
	}

	@Override
	public Service create(Service service) {
		return getIdentityClient().createService(service);
	}

	@Override
	public void deleteService(String id) {
		getIdentityClient().deleteService(id);
		
	}

	@Override
	public void deleteServices(String[] ids) {
		for(String id : ids) {
			deleteService(id);
		}
		
	}

	@Override
	public List<Endpoint> listEndpoints() {
		return getIdentityClient().listEndpoints();
	}

	@Override
	public Endpoint create(Endpoint endpoint) {
		return getIdentityClient().createEndpoint(endpoint);
	}

	@Override
	public void deleteEndpoint(String id) {
		getIdentityClient().deleteEndpoint(id);
		
	}

	@Override
	public void deleteEndpoints(String[] ids) {
		for(String id : ids) {
			deleteEndpoint(id);
		}
		
	}

}
